package com.me.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
            packageName = "com.me.diary",
            includeInStartupProfile = true
        ) {
            pressHome()
            startActivityAndWait()
            device.findObject(By.text(button))?.click()
            device.findObject(By.scrollable(true))
                ?.fling(Direction.UP, SPEED)
            device.findObject(By.scrollable(true))
                ?.fling(Direction.DOWN, SPEED)
        }
    }

    companion object {
        private const val SPEED = 30_000
    }
}
