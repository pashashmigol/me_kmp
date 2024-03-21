@file:OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)

package screens.history.viewmodels.tags

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import data.Repository
import data.storage.StorageStub
import data.utils.now
import model.HashTag
import model.Mention
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals


class TagsViewModelRealTest {
    @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `add and clear tag`() = runTest {
        val repository = Repository(StorageStub())
        val tagViewModel = TagsViewModelReal(repository)

        repository.addTag(HashTag("#1", lastUsed = now()))
        repository.addTag(HashTag("#2", lastUsed = now()))

        delay(1000)

        tagViewModel.onTextChanged(TextFieldValue(text = "#"))
        checkNextValue(listOf("#1", "#2"), tagViewModel.suggestedTags)

        tagViewModel.onTextChanged(TextFieldValue(text = "#1"))
        checkNextValue(listOf("#1"), tagViewModel.suggestedTags)

        tagViewModel.onTextChanged(TextFieldValue(text = ""))
        checkNextValue(emptyList(), tagViewModel.suggestedTags)
    }

    @Test
    fun `add and clear mention`() = runBlocking {
        val repository = Repository(StorageStub())
        val tagViewModel = TagsViewModelReal(repository)

        repository.addMention(Mention("@1", lastUsed = now()))
        repository.addMention(Mention("@2", lastUsed = now()))

        tagViewModel.onTextChanged(TextFieldValue(text = "@"))
        checkNextValue(listOf("@1", "@2"), tagViewModel.suggestedTags)

        tagViewModel.onTextChanged(TextFieldValue(text = "@1"))
        checkNextValue(listOf("@1"), tagViewModel.suggestedTags)

        tagViewModel.onTextChanged(TextFieldValue(text = ""))
        checkNextValue(emptyList(), tagViewModel.suggestedTags)
    }

    private suspend fun checkNextValue(expected: List<String>, flow: StateFlow<List<String>>) {
        flow.test {
            var item1: List<String>? = null
            while (item1?.size != expected.size) {
                item1 = awaitItem()
                println("### wait for $expected, got $item1")
            }
            assertContentEquals(expected, item1)
        }
    }
}
