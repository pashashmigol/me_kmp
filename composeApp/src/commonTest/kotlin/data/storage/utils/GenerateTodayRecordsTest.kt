package data.storage.utils

import data.utils.now
import data.utils.todayRecords
import model.MoodRecord
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class GenerateTodayRecordsTest {

    @Test
    fun `generate today with one record only`() {
        val records = listOf(
            MoodRecord(text = "test1", date = now())
        )

        val todayRecords: List<MoodRecord> = todayRecords(records)

        assertEquals(1, todayRecords.size)
        assertContentEquals(records, todayRecords)
    }

    @Test
    fun `generate today with few records`() {
        val records = listOf(
            MoodRecord(text = "test1", date = now()),
            MoodRecord(text = "test2", date = now())
        )

        val todayRecords = todayRecords(records)

        assertEquals(2, todayRecords.size)
        assertContentEquals(records, todayRecords)
    }
}
