import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
abstract class RepeatableTest {
    init {
        newSingleThreadContext("UI thread").let {
            Dispatchers.setMain(it)
        }
    }
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