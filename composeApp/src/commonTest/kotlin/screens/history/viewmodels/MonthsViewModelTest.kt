package screens.history.viewmodels

import RepeatableTest
import data.Repository
import data.storage.StorageStub
import data.utils.now
import model.MoodRecord
import kotlinx.coroutines.Dispatchers
import waitForListWithSize
import kotlin.test.Test

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
}
