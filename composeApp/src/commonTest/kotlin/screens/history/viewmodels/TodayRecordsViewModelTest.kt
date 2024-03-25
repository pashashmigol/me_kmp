package screens.history.viewmodels

import RepeatableTest
import app.cash.turbine.test
import data.Repository
import data.storage.StorageStub
import data.utils.now
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import model.MoodRecord
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
class TodayRecordsViewModelTest : RepeatableTest() {
    init {
        newSingleThreadContext("UI thread").let { Dispatchers.setMain(it) }
    }

    private var repository: Repository? = null
    override fun beforeEach() {
        repository = Repository(StorageStub(), dispatcher = Dispatchers.Unconfined)
    }

    @Test
    fun `one record is added on today screen`() = runTest {
        val repository = Repository(StorageStub())
        val viewModel = TodayRecordsViewModel(repository)

        repository.addRecord(MoodRecord(date = now()))

        viewModel.records.test {
            skipItems(1)
            assertEquals(1, awaitItem().size)
        }
    }
}
