package screens.history.viewmodels

import app.cash.turbine.test
import data.Repository
import data.storage.StorageStub
import data.utils.now
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import model.MoodRecord
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
class TodayRecordsViewModelTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `one record is added on today screen`() = runTest {
        val repository = Repository(StorageStub())
        val viewModel = TodayRecordsViewModel(repository)

        repository.addRecord(MoodRecord(date = now()))

        viewModel.records.test {
            while ((awaitItem().isEmpty())) {
            }
            assertEquals(1, viewModel.records.first().size)
        }
    }
}
