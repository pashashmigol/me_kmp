package data.storage.utils

import data.utils.generateDays
import data.utils.now
import model.MoodRecord
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class GenerateDaysTest {

    @Test
    fun `generate day with one record only`() {
        val records = listOf(
            MoodRecord(text = "test1", date = now())
        )

        val days = generateDays(records)

        assertEquals(1, days.size)
        assertContentEquals(records, days.first().records)
    }

    @Test
    fun `generate day with few records`() {
        val records = listOf(
            MoodRecord(text = "test1", date = now()),
            MoodRecord(text = "test2", date = now())
        )

        val days = generateDays(records)

        assertEquals(1, days.size)
        assertContentEquals(records, days.first().records)
    }
}