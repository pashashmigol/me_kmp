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

class OneMonthRecordsViewModelTest : RepeatableTest() {
    private var repository: Repository? = null
    override fun beforeEach() {
        repository = Repository(StorageStub(), dispatcher = Dispatchers.Unconfined)
    }

    @Test
    fun `on item is added on the screen`() = runTest {
        val repository = Repository(StorageStub())

        val oneMonthRecordsViewModel = OneMonthRecordsViewModel(repository, 0)
        repository.addRecord(MoodRecord(date = now()))

        oneMonthRecordsViewModel.records.test {
            skipItems(1)
            assertEquals(1, awaitItem().size)
        }
    }
}