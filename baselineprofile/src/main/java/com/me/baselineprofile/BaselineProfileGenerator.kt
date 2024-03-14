package com.me.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test class generates a basic startup baseline profile for the target package.
 *
 * We recommend you start with this but add important user flows to the profile to improve their performance.
 * Refer to the [baseline profile documentation](https://d.android.com/topic/performance/baselineprofiles)
 * for more information.
 *
 * You can run the generator with the "Generate Baseline Profile" run configuration in Android Studio or
 * the equivalent `generateBaselineProfile` gradle task:
 * ```
 * ./gradlew :composeApp:generateReleaseBaselineProfile
 * ```
 * The run configuration runs the Gradle task and applies filtering to run only the generators.
 *
 * Check [documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args)
 * for more information about available instrumentation arguments.
 *
 * After you run the generator, you can verify the improvements running the [ScrollBenchmarks] benchmark.
 *
 * When using this class to generate a baseline profile, only API 33+ or rooted API 28+ are supported.
 *
 * The minimum required version of androidx.benchmark to generate a baseline profile is 1.2.0.
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun scrollThroughDays() = scrollTopAndDown("days")

    @Test
    fun scrollThroughWeeks() = scrollTopAndDown("weeks")

    @Test
    fun scrollThroughMonths() = scrollTopAndDown("months")

    private fun scrollTopAndDown(button: String) {
        rule.collect(
            packageName = "com.me.multiplatform",
            includeInStartupProfile = true
        ) {
            pressHome()
            startActivityAndWait()
            device.findObject(By.text(button))?.click()

            device.findObject(By.scrollable(true))?.fling(Direction.UP, SPEED)
            device.findObject(By.scrollable(true))?.fling(Direction.DOWN, SPEED)
        }
    }

    companion object {
        private const val SPEED = 30_000
    }
}
