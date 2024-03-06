package screens.components.cards.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import kotlin.text.Regex.Companion.fromLiteral

internal fun highlightSearch(
    text: String,
    toSearch: String
): AnnotatedString = buildAnnotatedString {
    append(text)

    toSearch.split(" ")
        .filter { it.isNotBlank() }
        .forEach { search ->
            val regex =  fromLiteral(search.trim())
            var matchResult: MatchResult? = regex.find(text)

            while (matchResult != null) {
                addStyle(
                    style = SpanStyle(background = Color(163, 42, 34)),
                    start = matchResult.range.first,
                    end = matchResult.range.last + 1
                )
                matchResult = matchResult.next()
            }
        }
}