package presentation.history


import data.utils.filteredRecords
import data.utils.now
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import model.Anger
import model.MoodRecord
import model.Surprise
import screens.history.viewmodels.RecordsFilter
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
class FilteredRecordsTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `filter by emotions`() {
        val allRecords = listOf(
            MoodRecord(feelings = listOf(Anger.Annoyed), date = now()),
            MoodRecord(feelings = listOf(Anger.Disgusted), date = now()),
            MoodRecord(feelings = listOf(Surprise.Amazed), date = now()),
            MoodRecord(feelings = listOf(Surprise.Bewildered), date = now()),
        )
        val records = filteredRecords(
            unfilteredRecords = allRecords,
            filter = RecordsFilter(text = "", emotions = listOf(Anger)),
        )
        assertEquals(2, records.size)
        assertEquals(
            expected = MoodRecord(
                feelings = listOf(Anger.Annoyed),
                date = now()
            ).feelings.first(),
            actual = records.first().feelings.first()
        )
    }

    @Test
    fun `filter by text`() {
        val allRecords = listOf(
            MoodRecord(text = "pasha", date = now()),
            MoodRecord(text = "sasha", date = now()),
            MoodRecord(text = "dasha", date = now()),
            MoodRecord(text = "pesha", date = now()),
        )
        val filteredRecords = filteredRecords(
            unfilteredRecords = allRecords,
            filter = RecordsFilter(text = "asha", emotions = listOf()),
        )
        assertEquals(3, filteredRecords.size)
    }
}
