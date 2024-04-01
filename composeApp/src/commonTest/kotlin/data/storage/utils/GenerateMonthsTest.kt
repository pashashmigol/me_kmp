package data.storage.utils

import data.utils.generateMonths
import data.utils.minus
import data.utils.now
import data.utils.plus
import model.MoodRecord
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days

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

    @Test
    fun `indexes of generated months are properly sorted`() {
        val records = listOf(
            MoodRecord(text = "test1", date = now() - 31.days),
            MoodRecord(text = "test2", date = now() + 31.days),
            MoodRecord(text = "test2", date = now() - 62.days),
            MoodRecord(text = "test2", date = now() + 62.days)
        )

        val months = generateMonths(records)
        assertEquals(4, months.size)

        months.zipWithNext { left, right ->
            assertTrue { left.index == right.index - 1 }
        }
    }
}
