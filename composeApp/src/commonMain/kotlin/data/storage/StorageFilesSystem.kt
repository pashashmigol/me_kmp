package data.storage

import com.me.data.storage.utils.MoodRecordAdapter
import model.HashTag
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.addAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import korlibs.time.DateFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Mention
import model.MoodRecord
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import javax.inject.Inject

@OptIn(ExperimentalStdlibApi::class)
class StorageFilesSystem
@Inject constructor(@ApplicationContext private val context: Context) : Storage {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val moshi: Moshi = Moshi.Builder()
        .addAdapter(MoodRecordAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    private val hashTagAdapter = moshi.adapter<List<HashTag>>().lenient()
    private val mentionsAdapter = moshi.adapter<List<Mention>>().lenient()
    private val moodRecordAdapter = moshi.adapter<MoodRecord>().lenient()

    override suspend fun addRecord(moodRecord: MoodRecord) {
        scope.launch(Dispatchers.IO) {
            addRecordSync(moodRecord)
        }
    }

    fun addRecordSync(moodRecord: MoodRecord) {
        val file = File(recordsFolder(), moodRecord.date.toString())
            .apply { createNewFile() }

        val writer = JsonWriter.of(file.outputStream().sink().buffer())
        moodRecordAdapter.toJson(
            writer,
            moodRecord
        )
        writer.close()
    }

    override suspend fun allRecords(): List<MoodRecord> = recordsFolder().listFiles()?.mapNotNull {
        moodRecordAdapter.fromJson(it.inputStream().source().buffer())
    } ?: listOf()

    fun deleteAllSync() {
        recordsFolder().deleteRecursively()
    }

    override suspend fun tags(): List<HashTag> = hashTagAdapter.runCatching {
        fromJson(tagsFile().inputStream().source().buffer())
    }.getOrNull() ?: emptyList()

    override suspend fun addTag(tag: HashTag) {
        scope.launch(Dispatchers.IO) {
            addTagSync(tag)
        }
    }

    fun addTagSync(tag: HashTag) {
        val tags = hashTagAdapter.runCatching {
            fromJson(tagsFile().inputStream().source().buffer())
        }.getOrNull() ?: emptyList()

        val writer = JsonWriter.of(tagsFile().outputStream().sink().buffer())
        hashTagAdapter.toJson(writer, tags + tag)
        writer.close()
    }

    override suspend fun mentions(): List<Mention> = mentionsAdapter.runCatching {
        fromJson(mentionsFile().inputStream().source().buffer())
    }.getOrNull() ?: emptyList()

    override suspend fun addMention(mention: Mention) {
        scope.launch(Dispatchers.IO) {
            addMentionSync(mention)
        }
    }

    fun addMentionSync(mention: Mention) {
        val mentions = mentionsAdapter.runCatching {
            fromJson(mentionsFile().inputStream().source().buffer())
        }.getOrNull() ?: emptyList()

        val writer = JsonWriter.of(mentionsFile().outputStream().sink().buffer())
        mentionsAdapter.toJson(writer, mentions + mention)
        writer.close()
    }

    private fun recordsFolder(): File {
        val folder = File(context.filesDir, "records")
        if (!folder.exists()) folder.mkdir()
        return folder
    }

    private fun tagsFile(): File = File(tagsFolder(), "tags")
        .apply { if (!exists()) createNewFile() }

    private fun mentionsFile(): File = File(tagsFolder(), "mentions")
        .apply { if (!exists()) createNewFile() }

    private fun tagsFolder(): File {
        val folder = File(context.filesDir, "tags")
        if (!folder.exists()) folder.mkdir()
        return folder
    }
}

const val FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz"
