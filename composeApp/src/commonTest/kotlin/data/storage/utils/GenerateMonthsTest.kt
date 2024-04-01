package data.storage.utils

import data.utils.generateMonths
import data.utils.now
import model.MoodRecord
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class GenerateMonthsTest {

    @Test
    fun `generate month with one record only`() {
        val records = listOf(
            MoodRecord(text = "test1", date = now())
        )

        val monthRecords = generateMonths(records)

        assertEquals(1, monthRecords.size)
        assertContentEquals(records, monthRecords.first().records)
    }

    @Test
    fun `generate month with few records`() {
        val records = listOf(
            MoodRecord(text = "test1", date = now()),
            MoodRecord(text = "test2", date = now())
        )

        val monthRecords = generateMonths(records)

        assertEquals(1, monthRecords.size)
        assertContentEquals(records, monthRecords.first().records)
    }
}
