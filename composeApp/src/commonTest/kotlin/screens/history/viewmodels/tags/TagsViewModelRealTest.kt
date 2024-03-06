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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class TagsViewModelRealTest {
    @OptIn(DelicateCoroutinesApi::class)
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

        tagViewModel.onTextChanged(TextFieldValue(text = "#"))

        assertContentEquals(
            arrayOf("#1", "#2"),
            tagViewModel.suggestedTags.first().toTypedArray()
        )

        tagViewModel.onTextChanged(TextFieldValue(text = "#1"))
        assertContentEquals(
            arrayOf("#1"),
            tagViewModel.suggestedTags.first().toTypedArray()
        )

        tagViewModel.onTextChanged(TextFieldValue(text = ""))
        assertTrue(tagViewModel.suggestedTags.first().isEmpty())
    }

    @Test
    fun `add and clear mention`() = runTest {
        val repository = Repository(StorageStub())
        val tagViewModel = TagsViewModelReal(repository)

        repository.addMention(Mention("@1", lastUsed = now()))
        repository.addMention(Mention("@2", lastUsed = now()))

        tagViewModel.onTextChanged(TextFieldValue(text = "@"))
        tagViewModel.suggestedTags.test {
            assertContentEquals(
                listOf("@1", "@2"),
                awaitItem()
            )
        }

        tagViewModel.onTextChanged(TextFieldValue(text = "@1"))
        assertContentEquals(
            listOf("@1"),
            tagViewModel.suggestedTags.first()
        )

        tagViewModel.onTextChanged(TextFieldValue(text = ""))
        assertTrue(tagViewModel.suggestedTags.first().isEmpty())
    }
}
