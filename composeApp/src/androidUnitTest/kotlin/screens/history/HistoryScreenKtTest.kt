@file:Suppress("SameParameterValue")

package screens.history

import data.Repository
import data.storage.StorageStub
import screens.history.viewmodels.tags.TagsViewModelReal
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.me.diary.EmptyTestActivity
import com.me.screens.clearText
import model.HashTag
import model.Mention
import model.MoodRecord
import com.me.screens.clickHashTag
import com.me.screens.clickMention
import com.me.screens.enterText
import screens.history.viewmodels.DaysViewModel
import com.me.screens.noShownSuggestions
import com.me.screens.noViewWithText
import com.me.screens.shownViewWithText
import data.utils.now
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HistoryScreenKtTest {
    private val storage = StorageStub()
    private val repo = Repository(storage)

    private val tagsViewModel = TagsViewModelReal(repo)

    @get:Rule
    val composeTestRule: AndroidComposeTestRule<*, *> =
        createAndroidComposeRule<EmptyTestActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            HistoryScreen(
                historyViewModel = DaysViewModel(repo),
                tagsViewModel = tagsViewModel
            )
        }
        repo.addTag(HashTag("#1", now()))
        repo.addMention(Mention("@1", now()))
    }

    @Test
    fun `type # and check suggestion`() {
        composeTestRule.enterText("#")
        composeTestRule.shownViewWithText("#1")

        composeTestRule.clearText()
        composeTestRule.noShownSuggestions()
    }

    @Test
    fun `type @ and check suggestion`() {
        composeTestRule.enterText("@")
        composeTestRule.shownViewWithText("@1")

        composeTestRule.clearText()
        composeTestRule.noShownSuggestions()
    }

    @Test
    fun `click tag button and check suggestion`() {
        composeTestRule.clickHashTag()
        composeTestRule.shownViewWithText("#1")

        composeTestRule.clearText()
        composeTestRule.noShownSuggestions()
    }

    @Test
    fun `type mention button and check suggestion`() {
        composeTestRule.clickMention()
        composeTestRule.shownViewWithText("@1")

        composeTestRule.clearText()
        composeTestRule.noShownSuggestions()
    }

    @Test
    fun `check filtering by text`() {
        repo.addRecord(MoodRecord(text = "text1", date = now()))
        repo.addRecord(MoodRecord(text = "text2", date = now()))

        composeTestRule.enterText("t")
        composeTestRule.shownViewWithText("text2")
    }

    @Test
    fun `check filtering by digits in text`() {
        repo.addRecord(MoodRecord(text = "text1", date = now()))
        repo.addRecord(MoodRecord(text = "text2", date = now()))

        composeTestRule.enterText("1")
        composeTestRule.shownViewWithText("text1")
        composeTestRule.noViewWithText("text2")
    }
}
