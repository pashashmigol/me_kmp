package presentation.history

import screens.history.viewmodels.tags.completeTag
import kotlin.test.Test
import kotlin.test.assertEquals

class TagsViewModelKtTest {

    @Test
    fun `simple case`() {
        check("hello @p", "@pasha", "hello @pasha ")
        check("hello @pasha, @pas", "@pasha", "hello @pasha, @pasha ")
        check("hello @sasha, ", "@pasha", "hello @sasha, ")
        check("@p", "@pasha", "@pasha ")
        check("@p ", "@pasha", "@p ")

        check("hello #p", "#pasha", "hello #pasha ")
        check("hello #pasha, #pas", "#pasha", "hello #pasha, #pasha ")
        check("hello #sasha, ", "#pasha", "hello #sasha, ")
        check("#p ", "#pasha", "#p ")
    }

    private fun check(original: String, tag: String, result: String) {
        val completedText = completeTag(
            current = original,
            tag = tag
        )
        assertEquals(result, completedText)
    }
}
