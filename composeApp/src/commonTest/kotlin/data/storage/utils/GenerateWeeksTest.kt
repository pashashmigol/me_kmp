package data.storage.utils

import data.utils.generateWeeks
import data.utils.now
import model.MoodRecord
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class GenerateWeeksTest {

    @Test
    fun `generate week with one record only`() {
        val records = listOf(
            MoodRecord(text = "test1", date = now())
        )

        val weeks = generateWeeks(records)

        assertEquals(1, weeks.size)
        assertContentEquals(records, weeks.first().records)
    }

    @Test
    fun `generate week with few records`() {
        val records = listOf(
            MoodRecord(text = "test1", date = now()),
            MoodRecord(text = "test2", date = now())
        )

        val weeks = generateWeeks(records)

        assertEquals(1, weeks.size)
        assertContentEquals(records, weeks.first().records)
    }
}
