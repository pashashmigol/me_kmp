package model

import data.utils.now
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MoodRecordTest {

    @Test
    fun `matches mood records`() {
        val moodRecord = MoodRecord(
            date = now(),
            text = "pericula",
            feelings = listOf(Anger.Disgusted)
        )

        assertTrue(moodRecord.matches(contains = "peri"))
        assertTrue(moodRecord.matches(emotions = listOf(Anger)))
        assertTrue(moodRecord.matches(contains = "peri", emotions = listOf(Anger)))

        assertFalse(moodRecord.matches(contains = "seri"))
        assertFalse(moodRecord.matches(emotions = listOf(Sad)))
        assertFalse(moodRecord.matches(contains = "seri", emotions = listOf(Anger)))
    }

    @Test
    fun `matches period records`() {
        val period = DayRecord(
            start = now(),
            records = listOf(
                MoodRecord(
                    date = now(),
                    text = "abc",
                    feelings = listOf(Anger.Disgusted)
                ),
                MoodRecord(
                    date = now(),
                    text = "def",
                    feelings = listOf(Anger.Annoyed)
                ),
                MoodRecord(
                    date = now(),
                    text = "def",
                    feelings = listOf(Sad.Bored)
                ),
            ), index = 3896
        )

        assertTrue(period.matches(contains = "abc "))
        assertTrue(period.matches(contains = " "))
        assertTrue(period.matches(emotions = listOf(Anger)))
        assertTrue(period.matches(contains = "def ", emotions = listOf(Anger)))

        assertFalse(period.matches(contains = "xyz"))
        assertFalse(period.matches(emotions = listOf(Happy)))
        assertFalse(period.matches(contains = "xyz", emotions = listOf(Anger)))
    }

    @Test
    fun `matches period with one item`() {
        val period = DayRecord(
            start = now(),
            records = listOf(
                MoodRecord(
                    date = now(),
                    text = "abc",
                    feelings = listOf(Anger.Disgusted)
                ),
            ), index = 2347
        )

        assertTrue(period.matches(contains = "abc"))
        assertTrue(period.matches(emotions = listOf(Anger)))
        assertTrue(period.matches(contains = "abc", emotions = listOf(Anger)))

        assertFalse(period.matches(contains = "xyz"))
        assertFalse(period.matches(emotions = listOf(Happy)))
        assertFalse(period.matches(contains = "xyz", emotions = listOf(Anger)))
    }


    @Test
    fun `matches period tags`() {
        val record = MoodRecord(
            date = now(),
            text = "@pasha",
            feelings = listOf(Anger.Disgusted)
        )

        assertTrue(record.matches(contains = "@pasha @masha  "))
        assertTrue(record.matches(contains = "@pasha "))
        assertTrue(record.matches(contains = " "))
        assertTrue(record.matches(contains = "@ "))

        assertFalse(record.matches(contains = "@dasha @masha  "))
        assertFalse(record.matches(contains = "@dasha"))
    }
}
