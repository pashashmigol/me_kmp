package data.storage

import model.HashTag
import model.Mention
import model.MoodRecord

class StorageStub : Storage {
    private val moodRecords = mutableListOf<MoodRecord>()
    private val _tags = mutableListOf<HashTag>()
    private val _mentions = mutableListOf<Mention>()

    override suspend fun allRecords() = moodRecords
    override suspend fun addRecord(moodRecord: MoodRecord) { moodRecords.add(moodRecord) }

    override suspend fun mentions() = _mentions
    override suspend fun tags() = _tags

    override suspend fun addMention(mention: Mention) { _mentions += mention }
    override suspend fun addTag(tag: HashTag) { _tags += tag }
}
