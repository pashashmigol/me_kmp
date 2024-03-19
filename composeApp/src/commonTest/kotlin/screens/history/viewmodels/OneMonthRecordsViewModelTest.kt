package screens.history.viewmodels

import app.cash.turbine.test
import data.Repository
import data.storage.StorageStub
import data.utils.now
import model.MoodRecord
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
class OneMonthRecordsViewModelTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
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