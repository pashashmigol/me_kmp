import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

abstract class RepeatableTest {
    abstract fun beforeEach()

    fun runTest(
        times: Int = 100,
        context: CoroutineContext = EmptyCoroutineContext,
        timeout: Duration = 10.seconds,
        testBody: suspend TestScope.() -> Unit
    ): TestResult {
        var res: TestResult? = null
        repeat(times) {
            println("### runTest($it)")
            beforeEach()
            res = runTest(context, timeout, testBody)
        }
        return res!!
    }
}