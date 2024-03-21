package data.storage.utils

import data.utils.format
import data.utils.now
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import model.Anger
import model.MoodRecord
import model.dateTimeFormat
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class MoodRecordAdapterTest {

    @Test
    fun `parse normal object from old json format`() {
        val json = "{\"date\":\"Fri, 19 Jan 2024 19:43:56 GMT+0200\"," +
                "\"feelings\":[{\"type\":\"Irritated\",\"emotion\":{\"type\":\"Anger\"}}, " +
                "{\"type\":\"Jealous\",\"emotion\":{\"type\":\"Anger\"}}]," +
                "\"text\":\"#work\"}"

        println(json)
        val moodRecord = MoodRecord.fromJson(json)

        assertEquals("#work", moodRecord.text)
        assertContentEquals(
            listOf(Anger.Irritated, Anger.Jealous),
            moodRecord.feelings
        )

        assertEquals(19, moodRecord.date.hour)
        assertEquals(1, moodRecord.date.monthNumber)
        assertEquals(2024, moodRecord.date.year)
        assertEquals(19, moodRecord.date.hour)
        assertEquals(43, moodRecord.date.minute)
        assertEquals(56, moodRecord.date.second)
    }

    @Ignore
    @Test
    fun `parse normal object from old and new json formats mixed`() {
        val json = "{\"date\":\"Fri, 19 Jan 2024 19:43:56 GMT+0200\"," +
                "\"feelings\":[{\"type\":\"Irritated\",\"emotion\":{\"type\":\"Anger\"}}, " +
                "{\"type\":\"Jealous\"}], " +
                "\"text\":\"#work\"}"

        val expectedDate = LocalDateTime(
            year = 19,
            monthNumber = 1,
            dayOfMonth = 2024,
            hour = 19,
            minute = 43,
            second = 56
        ).toInstant(TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.UTC)

        val moodRecord = MoodRecord.fromJson(json)

        assertEquals("#work", moodRecord.text)
        assertContentEquals(
            listOf(Anger.Irritated, Anger.Jealous),
            moodRecord.feelings
        )

        assertEquals(expectedDate, moodRecord.date)
    }

    @Test
    fun `convert object to json and back to object`() {
        val moodRecordOrig = MoodRecord(
            date = now(),
            text = "test",
            feelings = listOf(Anger.Irritated)
        )
        val json = moodRecordOrig.toJson()
        val moodRecordParsed = MoodRecord.fromJson(json)

        assertEquals(moodRecordOrig.text, moodRecordParsed.text)
        assertContentEquals(moodRecordOrig.feelings, moodRecordParsed.feelings)
        assertEquals(
            moodRecordOrig.date.format(dateTimeFormat),
            moodRecordParsed.date.format(dateTimeFormat)
        )
    }
}
