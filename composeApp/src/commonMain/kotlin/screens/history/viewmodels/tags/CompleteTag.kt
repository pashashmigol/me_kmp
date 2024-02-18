package screens.history.viewmodels.tags

internal fun completeTag(current: String, tag: String): String {
    val regex = Regex(TagsViewModelReal.TAG_PATTERN)
    val matchResult = regex.findAll(current).lastOrNull() ?: return current + tag

    return if (matchResult.range.last < current.lastIndex) {
        current
    } else {
        current.replaceAfterLast(
            delimiter = tag.first().toString(),
            replacement = "${tag.drop(1)} "
        )
    }
}
