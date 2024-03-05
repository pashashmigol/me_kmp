package model

import data.storage.FORMAT
import data.utils.format
import kotlin.test.DefaultAsserter.assertNotEquals
import kotlin.test.Test

class RecordTest {

    @Test
    fun `random Records have different dates`() {
        val date1 = MoodRecord.random().date.format(FORMAT)
        val date2 = MoodRecord.random().date.format(FORMAT)

        assertNotEquals("random Records should have different dates", date1, date2)
    }
}