package screens.history.viewmodels.tags

import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test


class CompleteTagKtTest {

    @Test
    fun `tags are correctly completed`() {
        check(current = "#", tag = "#pasha", expected = "#pasha ")
        check(current = "#pa", tag = "#pasha", expected = "#pasha ")
        check(current = "#sasha #pa", tag = "#pasha", expected = "#sasha #pasha ")
        check(current = "#sasha #", tag = "#pasha", expected = "#sasha #pasha ")
        check(current = "#sasha @#pa", tag = "#pasha", expected = "#sasha @#pasha ")
    }

    private fun check(current: String, tag: String, expected: String) {
        assertEquals("", expected, completeTag(current = current, tag = tag))
    }
}