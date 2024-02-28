package data.storage.utils

import data.utils.now
import model.Fear
import model.HashTag
import model.Mention
import model.MoodRecord
import model.Peaceful
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonExtTest {

    @Test
    fun `mood record serialization`() {
        val moodRecordOrigin = MoodRecord(
            date = now(),
            text = "test text",
            feelings = listOf(Peaceful.Accepted, Fear.Shy)
        )
        val json = moodRecordOrigin.toJson()
        val moodRecordFromJson = MoodRecord.fromJson(json)

        assertEquals(moodRecordOrigin, moodRecordFromJson)
    }

    @Test
    fun `mention serialization`() {
        val mentionsOrigin = listOf(
            Mention(value = "test mention", lastUsed = now()),
            Mention(value = "test mention", lastUsed = now()),
            Mention(value = "test mention", lastUsed = now()),
            Mention(value = "test mention", lastUsed = now()),
        )

        val json = mentionsToJson(mentionsOrigin)
        val mentionsFromJson = Mention.fromJson(json)

        assertEquals(mentionsOrigin, mentionsFromJson)
    }

    @Test
    fun `tags serialization`() {
        val mentionsOrigin = listOf(
            HashTag(value = "test HashTag", lastUsed = now()),
            HashTag(value = "test HashTag", lastUsed = now()),
            HashTag(value = "test HashTag", lastUsed = now()),
            HashTag(value = "test HashTag", lastUsed = now()),
        )

        val json = hashTagsToJson(mentionsOrigin)
        val mentionsFromJson = HashTag.fromJson(json)

        assertEquals(mentionsOrigin, mentionsFromJson)
    }
}