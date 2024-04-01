package data.storage.utils

import data.utils.generateDays
import data.utils.minus
import data.utils.now
import data.utils.plus
import model.MoodRecord
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days

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

    @Test
    fun `indexes of generated days are properly sorted`() {
        val records = listOf(
            MoodRecord(text = "test1", date = now() - 1.days),
            MoodRecord(text = "test2", date = now() + 1.days),
            MoodRecord(text = "test2", date = now() - 2.days),
            MoodRecord(text = "test2", date = now() + 2.days)
        )

        val days = generateDays(records)
        assertEquals(4, days.size)

        days.zipWithNext { left, right ->
            assertTrue { left.index == right.index - 1 }
        }
    }
}