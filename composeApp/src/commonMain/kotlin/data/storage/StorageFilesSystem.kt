package data.storage

import data.storage.utils.fromJson
import data.storage.utils.hashTagsToJson
import data.storage.utils.mentionsToJson
import data.storage.utils.toJson
import data.utils.DATE_TIME_FORMAT
import data.utils.format
import model.HashTag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import model.Mention
import model.MoodRecord
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.use

class StorageFilesSystem : Storage {
    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun addRecord(moodRecord: MoodRecord) {
        addRecordSync(moodRecord)
    }

    private fun addRecordSync(moodRecord: MoodRecord) {
        val filePath = recordsFolder().resolve(
            moodRecord.date.format(DATE_TIME_FORMAT).toPath()
        )
        val fileHandle = fileSystem().openReadWrite(filePath)

        fileHandle.sink().buffer().use {
            it.write(moodRecord.toJson().encodeToByteArray())
        }
    }

    override suspend fun allRecords(): List<MoodRecord> {
        fileSystem().createDirectory(recordsFolder())

        return fileSystem().list(recordsFolder()).map { path ->
            val json: String = fileSystem().read(path) {
                readUtf8()
            }
            if (json.isEmpty()) {
                return emptyList()
            }
            MoodRecord.fromJson(json)
        }
    }

    override suspend fun tags(): List<HashTag> {
        return if (fileSystem().exists(tagsFile())) {
            fileSystem()
                .read(tagsFile()) {
                    val utf = readUtf8()
                    println("###: tags: $utf")
                    utf
                }.let { HashTag.fromJson(it) }
        } else emptyList()
    }

    override suspend fun addTag(tag: HashTag) {
        scope.launch(Dispatchers.IO) {
            addTagSync(tag)
        }
    }

    private fun addTagSync(tag: HashTag) {
        fileSystem().createDirectory(tagsFolder())
        if (!fileSystem().exists(tagsFile())) {
            fileSystem().write(tagsFile()) {}
        }
        val json = fileSystem().read(tagsFile()) {
            readUtf8()
        }
        val tags = if (json.isEmpty()) {
            emptyList()
        } else {
            HashTag.fromJson(json)
        }
        fileSystem().write(tagsFile()) {
            write(hashTagsToJson(tags + tag).encodeToByteArray())
        }
    }

    override suspend fun mentions(): List<Mention> {
        return if (fileSystem().exists(mentionsFile())) {
            fileSystem()
                .read(mentionsFile()) {
                    val utf = readUtf8()
                    println("###: mentions: $utf")
                    utf
                }.let { Mention.fromJson(it) }
        } else emptyList()
    }

    override suspend fun addMention(mention: Mention) {
        scope.launch(Dispatchers.IO) {
            addMentionSync(mention)
        }
    }

    private fun addMentionSync(mention: Mention) {
        fileSystem().createDirectory(tagsFolder())
        if (!fileSystem().exists(mentionsFile())) {
            fileSystem().write(mentionsFile()) {}
        }
        val json = fileSystem().read(mentionsFile()) {
            readUtf8()
        }
        val mentions = if (json.isEmpty()) {
            emptyList()
        } else {
            Mention.fromJson(json)
        }
        fileSystem().write(mentionsFile()) {
            val str = mentionsToJson(mentions + mention)
            println("###; mentions str = $str")
            write(str.encodeToByteArray())
        }
    }

    private fun recordsFolder(): Path =
        (filesFolder() + "/records").toPath()

    private fun tagsFolder() =
        (filesFolder() + "/tags").toPath()

    private fun tagsFile(): Path = tagsFolder().resolve("tags")

    private fun mentionsFile(): Path = tagsFolder().resolve("mentions")
}

expect fun filesFolder(): String
