package screens.home

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.me.diary.EmptyTestActivity
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeScreenTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<EmptyTestActivity>()

    @Test
    fun `all buttons are presented on Home screen`() = runTest{
        composeTestRule.setContent {
            HomeScreen()
        }
        checkButton("new record")
        checkButton("days")
        checkButton("weeks")
        checkButton("months")
    }

    private fun checkButton(tag: String) {
        composeTestRule.onNode(hasTestTag(tag)).let {
            it.assertExists()
            it.assertIsEnabled()
        }
    }
}
