package screens.history.viewmodels

import RepeatableTest
import data.Repository
import data.storage.StorageStub
import data.utils.now
import model.MoodRecord
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import waitForListWithSize
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
class DaysViewModelTest : RepeatableTest() {
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
    fun `test with state`() = runTest {
        val repository = Repository(StorageStub())
        repository.addRecord(MoodRecord(date = now()))

        val viewModel1 = DaysViewModel(repository)
        val viewModel2 = DaysViewModel(repository)

        repository.addRecord(MoodRecord(date = now()))


//        viewModel1.records.collectAsState().value
    }
}
