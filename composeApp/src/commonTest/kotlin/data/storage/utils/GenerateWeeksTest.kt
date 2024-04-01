package data.storage.utils

import data.utils.generateWeeks
import data.utils.minus
import data.utils.now
import data.utils.plus
import model.MoodRecord
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days

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

    @Test
    fun `indexes of generated weeks are properly sorted`() {
        val records = listOf(
            MoodRecord(text = "test1", date = now() - 7.days),
            MoodRecord(text = "test2", date = now() + 7.days),
            MoodRecord(text = "test2", date = now() - 14.days),
            MoodRecord(text = "test2", date = now() + 14.days)
        )

        val weeks = generateWeeks(records)
        assertEquals(4, weeks.size)

        weeks.zipWithNext { left, right ->
            assertTrue { left.index == right.index - 1 }
        }
    }
}
