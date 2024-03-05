package model

import data.utils.date
import data.utils.now
import kotlin.test.Test
import kotlin.test.assertEquals

class WeekRecordTest {

    @Test
    fun getEmotions() {
        val startDate = date(year = 2000, month = 1, dayOfMonth = 11)
        val endDate = date(year = 2000, month = 1, dayOfMonth = 17)

        val weekRecord = WeekRecord(
            start = startDate,
            end = endDate,
            records = listOf(
                MoodRecord(feelings = listOf(Sad.Bored), date = now()),
                MoodRecord(feelings = listOf(Anger.Disgusted), date = now()),
                MoodRecord(feelings = listOf(Anger.Disgusted), date = now()),
                MoodRecord(feelings = listOf(Anger.Disgusted), date = now()),
                MoodRecord(feelings = listOf(Anger.Disgusted), date = now()),
                MoodRecord(feelings = listOf(Anger.Disgusted), date = now()),
                MoodRecord(feelings = listOf(Anger.Disgusted), date = now()),
                MoodRecord(feelings = listOf(Anger.Disgusted), date = now())
            ),
            index = 0
        )

        assertEquals(3, weekRecord.mainEmotions.size)
        assertEquals(Anger.Disgusted, weekRecord.mainEmotions[0])
        assertEquals(Anger.Disgusted, weekRecord.mainEmotions[1])
        assertEquals(Anger.Disgusted, weekRecord.mainEmotions[2])
    }
}