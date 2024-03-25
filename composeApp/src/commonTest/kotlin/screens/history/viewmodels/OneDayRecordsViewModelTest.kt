package screens.history.viewmodels

import RepeatableTest
import app.cash.turbine.test
import data.Repository
import data.storage.StorageStub
import data.utils.now
import model.MoodRecord
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
class OneDayRecordsViewModelTest : RepeatableTest() {
    init {
        newSingleThreadContext("UI thread").let {
            Dispatchers.setMain(it)
        }
    }

    private var repository: Repository? = null

    override fun beforeEach() {
        repository = Repository(StorageStub(), dispatcher = Dispatchers.Unconfined)
    }

    @Test
    fun `on item is added on the screen`() = runTest(times = 100) {
        val repository = Repository(StorageStub())

        val viewModel = OneDayRecordsViewModel(repository, 0)

        repository.addRecord(MoodRecord(date = now()))

        viewModel.records.test {
            awaitItem()
            assertEquals(1, awaitItem().size)
        }
    }
}

