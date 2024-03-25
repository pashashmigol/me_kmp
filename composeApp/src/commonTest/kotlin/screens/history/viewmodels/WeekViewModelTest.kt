package screens.history.viewmodels

import RepeatableTest
import app.cash.turbine.test
import data.Repository
import data.storage.StorageStub
import data.utils.now
import model.MoodRecord
import kotlinx.coroutines.Dispatchers
import kotlin.test.Test
import kotlin.test.assertEquals

class WeekViewModelTest : RepeatableTest() {
    private var repository: Repository? = null
    override fun beforeEach() {
        repository = Repository(StorageStub(), dispatcher = Dispatchers.Unconfined)
    }

    @Test
    fun `one item is added on screen`() = runTest {
        val repository = Repository(StorageStub())
        val viewModel = WeeksViewModel(repository)

        repository.addRecord(MoodRecord(date = now()))

        viewModel.records.test {
            skipItems(1)
            assertEquals(1, awaitItem().size)
        }
    }
}
