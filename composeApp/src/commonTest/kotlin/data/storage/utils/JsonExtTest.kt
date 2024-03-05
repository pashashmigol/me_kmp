package data.storage.utils

import data.utils.now
import data.utils.startOfDay
import kotlinx.serialization.json.Json
import model.Fear
import model.Feeling
import model.HashTag
import model.Mention
import model.MoodRecord
import model.Peaceful
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonExtTest {
    @Test
    fun `feeling deserialization`() {
        val json = "{\"type\":\"Accepted\"}"
        val feeling = Json.decodeFromString<Feeling>(json)

        assertEquals(Peaceful.Accepted, feeling)
    }

    @Test
    fun `mood record serialization`() {
        val moodRecordOrigin = MoodRecord(
            date = now().startOfDay,
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