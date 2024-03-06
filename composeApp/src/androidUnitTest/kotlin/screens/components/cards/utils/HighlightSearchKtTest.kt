package screens.components.cards.utils

import org.junit.Test

class HighlightSearchKtTest {

    @Test
    fun `test that function doesn't fail`() {
        highlightSearch(
            text = "any string",
            toSearch = "(yes"
        )
    }
}