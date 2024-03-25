import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

suspend inline fun <reified T> waitForTags(
    expected: List<T>,
    flow: StateFlow<MutableMap<String, T>>
) {
    var got: Array<T>? = null
    while (!got.contentEquals(expected.toTypedArray())) {
        got = flow.first().values.toList().toTypedArray()
        delay(100)
    }
    assertContentEquals(expected, got?.toList())
}

suspend inline fun <reified T> waitForList(
    expected: List<T>,
    flow: StateFlow<List<T>>
) {
    var got: Array<T>? = null
    while (!got.contentEquals(expected.toTypedArray())) {
        got = flow.first().toTypedArray()
        delay(100)
    }
    assertContentEquals(expected, got?.toList())
}

suspend fun <T> waitForListWithSize(
    expectedSize: Int,
    flow: StateFlow<List<T>>
) {
    var got: Int? = null
    while (got != (expectedSize)) {
        got = flow.first().size
        delay(100)
    }
    assertEquals(expectedSize, got)
}