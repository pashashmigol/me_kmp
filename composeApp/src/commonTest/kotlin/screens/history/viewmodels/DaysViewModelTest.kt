package screens.history.viewmodels

import RepeatableTest
import data.Repository
import data.storage.StorageStub
import data.utils.now
import data.utils.plus
import data.utils.startOfDay
import model.MoodRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import model.DayRecord
import waitForListWithSize
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.time.Duration.Companion.hours

class DaysViewModelTest : RepeatableTest() {

    private var repository: Repository? = null
    override fun beforeEach() {
        repository = Repository(StorageStub(), dispatcher = Dispatchers.Unconfined)
    }

    @Test
    fun `on item is added on the screen`() = runTest {
        val repository = Repository(StorageStub())
        val viewModel = DaysViewModel(repository)

        repository.addRecord(MoodRecord(date = now()))
        waitForListWithSize(1, viewModel.records)
    }

    @Test
    fun `two view models`() = runTest {
        val repository = Repository(StorageStub())
        repository.addRecord(MoodRecord(date = now()))

        val viewModel1 = DaysViewModel(repository)
        val viewModel2 = DaysViewModel(repository)

        waitForListWithSize(1, viewModel1.records)
        waitForListWithSize(1, viewModel2.records)
    }

    @Test
    fun `days view model is created after the repo`() = runTest(times = 100) {
        val todayRecordsViewModel = TodayRecordsViewModel(repository!!)

        val records = listOf(
            MoodRecord(text = "test1", date = now().startOfDay),
            MoodRecord(text = "test2", date = now().startOfDay + 1.hours)
        )
        records.forEach {
            todayRecordsViewModel.addRecord(it)
        }

        val viewModel = DaysViewModel(repository!!)

        waitForListWithSize(1, viewModel.records)

        assertContentEquals(
            records,
            (viewModel.records.first().first() as DayRecord).records
        )
    }
}
