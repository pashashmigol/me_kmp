package data.storage

import model.HashTag
import model.Mention
import model.MoodRecord

interface Storage {
    suspend fun allRecords(): List<MoodRecord>

    suspend fun tags(): List<HashTag>
    suspend fun addTag(tag: HashTag)

    suspend fun mentions(): List<Mention>
    suspend fun addMention(mention: Mention)
    suspend fun addRecord(moodRecord: MoodRecord)
}
