@file:OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)

package screens.history.viewmodels.tags

import RepeatableTest
import androidx.compose.ui.text.input.TextFieldValue
import data.Repository
import data.storage.StorageStub
import data.utils.now
import model.HashTag
import model.Mention
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import waitForList
import kotlin.test.Ignore
import kotlin.test.Test

class TagsViewModelRealTest : RepeatableTest() {
    init {
        newSingleThreadContext("UI thread").let { Dispatchers.setMain(it) }
    }

    private var repository: Repository? = null
    override fun beforeEach() {
        repository = Repository(StorageStub(), dispatcher = Dispatchers.Unconfined)
    }

    @Ignore
    @Test
    fun `add and clear tag`() = runTest {
        val repository = Repository(StorageStub())
        val tagViewModel = TagsViewModelReal(repository)

        repository.addTag(HashTag("#1", lastUsed = now()))
        repository.addTag(HashTag("#2", lastUsed = now()))

        delay(1000)

        tagViewModel.onTextChanged(TextFieldValue(text = "#"))
        waitForList(listOf("#1", "#2"), tagViewModel.suggestedTags)

        tagViewModel.onTextChanged(TextFieldValue(text = "#1"))
        waitForList(listOf("#1"), tagViewModel.suggestedTags)

        tagViewModel.onTextChanged(TextFieldValue(text = ""))
        waitForList(emptyList(), tagViewModel.suggestedTags)
    }

    @Ignore
    @Test
    fun `add and clear mention`() = runBlocking {
        val repository = Repository(StorageStub())
        val tagViewModel = TagsViewModelReal(repository)

        repository.addMention(Mention("@1", lastUsed = now()))
        repository.addMention(Mention("@2", lastUsed = now()))

        tagViewModel.onTextChanged(TextFieldValue(text = "@"))
        waitForList(listOf("@1", "@2"), tagViewModel.suggestedTags)

        tagViewModel.onTextChanged(TextFieldValue(text = "@1"))
        waitForList(listOf("@1"), tagViewModel.suggestedTags)

        tagViewModel.onTextChanged(TextFieldValue(text = ""))
        waitForList(emptyList(), tagViewModel.suggestedTags)
    }
}
