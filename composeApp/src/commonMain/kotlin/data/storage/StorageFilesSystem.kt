package data.storage

//import data.storage.utils.MoodRecordAdapter
import data.storage.utils.fromJson
import data.storage.utils.toJson
import model.HashTag
//import com.squareup.moshi.JsonWriter
//import com.squareup.moshi.Moshi
//import com.squareup.moshi.adapter
//import com.squareup.moshi.addAdapter
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
//import dagger.hilt.android.qualifiers.ApplicationContext
//import korlibs.time.DateFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import model.Mention
import model.MoodRecord
import okio.FileHandle
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.Source
import okio.buffer
import okio.use

//import okio.sink
//import okio.source
//import java.io.File
//import javax.inject.Inject
@OptIn(ExperimentalStdlibApi::class)
class StorageFilesSystem
//@Inject
constructor(/*@ApplicationContext private val context: Context*/) : Storage {

    private val scope = CoroutineScope(Dispatchers.IO)
//    private val moshi: Moshi = Moshi.Builder()
//        .addAdapter(data.storage.utils.MoodRecordAdapter())
//        .add(KotlinJsonAdapterFactory())
//        .build()

//    private val hashTagAdapter = moshi.adapter<List<HashTag>>().lenient()
//    private val mentionsAdapter = moshi.adapter<List<Mention>>().lenient()
//    private val moodRecordAdapter = moshi.adapter<MoodRecord>().lenient()

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

    override suspend fun allRecords(): List<MoodRecord> =
        fileSystem().list(recordsFolder()).map { path ->
            val json: String = fileSystem().read(path) {
                readUtf8LineStrict()
            }
            MoodRecord.fromJson(json)
        }

    fun deleteAllSync() {
        fileSystem().deleteRecursively(recordsFolder())
    }

//    override suspend fun tags(): List<HashTag> = hashTagAdapter.runCatching {
//        fromJson(tagsFile().inputStream().source().buffer())
//    }.getOrNull() ?: emptyList()

    override suspend fun tags(): List<HashTag> = fileSystem()
        .read(tagsFile()) {
            readUtf8LineStrict()
        }.let { HashTag.fromJson(it) }

    override suspend fun addTag(tag: HashTag) {
        scope.launch(Dispatchers.IO) {
            addTagSync(tag)
        }
    }

//    private fun addTagSync(tag: HashTag) {
//        val tags = hashTagAdapter.runCatching {
//            fromJson(tagsFile().inputStream().source().buffer())
//        }.getOrNull() ?: emptyList()
//
//        val writer = JsonWriter.of(tagsFile().outputStream().sink().buffer())
//        hashTagAdapter.toJson(writer, tags + tag)
//        writer.close()
//    }

    private fun addTagSync(tag: HashTag) {
        val tags = fileSystem().read(tagsFile()) {
            readUtf8LineStrict()
        }.let { HashTag.fromJson(it) }

        fileSystem().write(tagsFile()){
            write((tags + tag).toJson().encodeToByteArray())
        }
    }

    override suspend fun mentions(): List<Mention> = fileSystem()
        .read(mentionsFile()) {
            readUtf8LineStrict()
        }.let { Mention.fromJson(it) }

    override suspend fun addMention(mention: Mention) {
        scope.launch(Dispatchers.IO) {
            addMentionSync(mention)
        }
    }

    fun addMentionSync(mention: Mention) {
        val mentions = fileSystem().read(mentionsFile()) {
            readUtf8LineStrict()
        }.let { Mention.fromJson(it) }

        fileSystem().write(mentionsFile()){
            write((mentions + mention).toJson().encodeToByteArray())
        }
    }

    private fun recordsFolder(): Path = "records".toPath()
//    : Path {
//
//        val fs = fileSystem()
////        val folder = File(context.filesDir, "records")
//        "records".toPath()
//        val folder: Source = fileSystem().source("records".toPath()).
//
//
//        if (!folder.exists()) folder.mkdir()
//        return folder
//    }

    //    private fun tagsFile(): File = File(tagsFolder(), "tags")
//        .apply { if (!exists()) createNewFile() }
    private fun tagsFile(): Path = tagsFolder().resolve("tags")

    //    private fun mentionsFile(): File = File(tagsFolder(), "mentions")
//        .apply { if (!exists()) createNewFile() }
    private fun mentionsFile(): Path = tagsFolder().resolve("mentions")

    //    private fun tagsFolder(): File {
//        val folder = File(context.filesDir, "tags")
//        if (!folder.exists()) folder.mkdir()
//        return folder
//    }
    private fun tagsFolder() = "tags".toPath()
}


const val FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz"
