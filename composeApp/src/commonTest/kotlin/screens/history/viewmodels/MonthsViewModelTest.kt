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
import waitForListWithSize
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.time.Duration.Companion.hours

class MonthsViewModelTest : RepeatableTest() {

    private var repository: Repository? = null
    override fun beforeEach() {
        repository = Repository(StorageStub(), dispatcher = Dispatchers.Unconfined)
    }

    @Test
    fun `on item is added on the screen`() = runTest {
        val repository = Repository(StorageStub())
        val viewModel = MonthsViewModel(repository)

        repository.addRecord(MoodRecord(date = now()))

        waitForListWithSize(1, viewModel.records)
    }

    @Test
    fun `months view model is created after the repo`() = runTest(times = 1) {
        val todayRecordsViewModel = TodayRecordsViewModel(repository!!)

        val records = listOf(
            MoodRecord(text = "test1", date = now().startOfDay),
            MoodRecord(text = "test2", date = now().startOfDay + 1.hours)
        )
        records.forEach {
            todayRecordsViewModel.addRecord(it)
        }

        val viewModel = MonthsViewModel(repository!!)

        waitForListWithSize(1, viewModel.records)

        assertContentEquals(
            records,
            viewModel.records.first().first().records
        )
    }
}
