package data.storage

import data.storage.utils.fromJson
import data.storage.utils.hashTagsToJson
import data.storage.utils.mentionsToJson
import data.storage.utils.toJson
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
        scope.launch(Dispatchers.IO) {
            addRecordSync(moodRecord)
        }
    }

    fun addRecordSync(moodRecord: MoodRecord) {
        val filePath = recordsFolder().resolve(moodRecord.date.toString().toPath())
        val fileHandle = fileSystem().openReadWrite(filePath)

        fileHandle.sink().buffer().use {
            it.write(moodRecord.toJson().encodeToByteArray())
        }
    }

    override suspend fun allRecords(): List<MoodRecord> {
        fileSystem().createDirectory(recordsFolder())

        return fileSystem().list(recordsFolder()).map { path ->
            val json: String = fileSystem().read(path) {
                readUtf8LineStrict()
            }
            MoodRecord.fromJson(json)
        }
    }

    fun deleteAllSync() {
        fileSystem().deleteRecursively(recordsFolder())
    }

    override suspend fun tags(): List<HashTag> {
        return if (fileSystem().exists(tagsFile())) {
            fileSystem()
                .read(tagsFile()) {
                    readUtf8LineStrict()
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
        fileSystem().write(tagsFile()) {}

        val tags = fileSystem().read(tagsFile()) {
            readUtf8LineStrict()
        }.let { HashTag.fromJson(it) }

        fileSystem().write(tagsFile()) {
            write(hashTagsToJson(tags + tag).encodeToByteArray())
        }
    }

    override suspend fun mentions(): List<Mention> {
        fileSystem().createDirectory(tagsFolder())
        fileSystem().write(mentionsFile()) {}

        return if (fileSystem().exists(tagsFile())) {
            fileSystem()
                .read(mentionsFile()) {
                    readUtf8LineStrict()
                }.let { Mention.fromJson(it) }
        } else emptyList()
    }

    override suspend fun addMention(mention: Mention) {
        scope.launch(Dispatchers.IO) {
            addMentionSync(mention)
        }
    }

    fun addMentionSync(mention: Mention) {
        val mentions = fileSystem().read(mentionsFile()) {
            readUtf8LineStrict()
        }.let { Mention.fromJson(it) }

        fileSystem().write(mentionsFile()) {
            write(mentionsToJson(mentions + mention).encodeToByteArray())
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

const val FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz"
