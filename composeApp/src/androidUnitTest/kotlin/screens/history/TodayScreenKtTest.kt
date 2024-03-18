@file:Suppress("SameParameterValue")

package screens.history

import data.Repository
import data.storage.StorageStub
import screens.history.viewmodels.tags.TagsViewModelReal
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.me.diary.EmptyTestActivity
import model.HashTag
import model.Mention
import com.me.screens.bigWheelIsHidden
import com.me.screens.bigWheelIsShown
import com.me.screens.clearText
import com.me.screens.clickBigWheel
import com.me.screens.clickCancel
import com.me.screens.clickHashTag
import com.me.screens.clickMention
import com.me.screens.clickOk
import com.me.screens.clickSmallWheel
import screens.components.wheel.WheelViewModelReal
import com.me.screens.elementPresented
import com.me.screens.enterText
import screens.history.viewmodels.TodayRecordsViewModel
import screens.history.viewmodels.draft.DraftRecordViewModelReal
import com.me.screens.inputFieldHasText
import com.me.screens.newRecordIsAddedWithText
import com.me.screens.newRecordIsNotAdded
import com.me.screens.noShownSuggestions
import com.me.screens.recordWithNoTextIsAdded
import com.me.screens.shownViewWithText
import com.me.screens.smallWheelHasNoSelection
import com.me.screens.smallWheelHasSelection
import data.utils.now
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.time.Duration.Companion.seconds

@RunWith(RobolectricTestRunner::class)
class TodayScreenKtTest {
    private val storage = StorageStub()
    private val repo = Repository(storage)
    private val tagsViewModel = TagsViewModelReal(repo)

    @get:Rule
    val composeTestRule: AndroidComposeTestRule<*, *> =
        createAndroidComposeRule<EmptyTestActivity>()

    @Before
    fun setup() {
        repo.addTag(HashTag("#1", now()))
        repo.addMention(Mention("@1", now()))
        composeTestRule.setContent {
            TodayScreen(
                historyViewModel = TodayRecordsViewModel(repo),
                draftViewModel = DraftRecordViewModelReal(tagsViewModel),
                wheelViewModel = WheelViewModelReal(),
                tagsViewModel = tagsViewModel
            )
        }
    }

    @Test
    fun `all elements are presented on screen start`() = runTest {
        composeTestRule.elementPresented("hashtag button")
        composeTestRule.elementPresented("mention button")
        composeTestRule.elementPresented("cancel button")
        composeTestRule.elementPresented("ok button")
        composeTestRule.elementPresented("text input")
    }

    @Test
    fun `add record successful flow`() = runTest {
        val textToAdd = "new record"

        composeTestRule.inputFieldHasText("")

        composeTestRule.enterText(textToAdd)
        composeTestRule.inputFieldHasText(textToAdd)

        composeTestRule.clickSmallWheel()
        composeTestRule.bigWheelIsShown()

        //first click selects an emotion, so big wheel stays shown
        composeTestRule.clickBigWheel()
        composeTestRule.bigWheelIsShown()

        //it takes two clicks to select a feeling
        composeTestRule.clickBigWheel()
        composeTestRule.bigWheelIsHidden()

        //small wheel now has selected emotion
        composeTestRule.smallWheelHasSelection()
        composeTestRule.inputFieldHasText(textToAdd)

        //add record
        composeTestRule.clickOk()

        //after new record is added both text field and small wheel are cleared
        composeTestRule.inputFieldHasText("")
        composeTestRule.smallWheelHasNoSelection()

        composeTestRule.newRecordIsAddedWithText(textToAdd)
    }

    @Test
    fun `cancel adding`() = runTest {
        val textToAdd = "record to cancel"
        composeTestRule.enterText(textToAdd)

        //add feeling
        composeTestRule.clickSmallWheel()
        composeTestRule.clickBigWheel()
        composeTestRule.clickBigWheel()

        composeTestRule.clickCancel()

        //everything is cleared,  nothing is added
        composeTestRule.inputFieldHasText("")
        composeTestRule.smallWheelHasNoSelection()
        composeTestRule.newRecordIsNotAdded()
    }

    @Test
    fun `add record with no text`() = runTest {
        //add feeling
        composeTestRule.clickSmallWheel()
        composeTestRule.clickBigWheel()
        composeTestRule.clickBigWheel()

        composeTestRule.clickOk()

        //record should be added anyway
        composeTestRule.recordWithNoTextIsAdded()
        //emotion selection should be cleared
        composeTestRule.smallWheelHasNoSelection()
    }

    @Test
    fun `try to add record with no feeling selected`() = runTest {
        val textToAdd = "record to cancel"
        composeTestRule.enterText(textToAdd)

        composeTestRule.smallWheelHasNoSelection()

        //nothing is added
        composeTestRule.newRecordIsNotAdded()
    }

    @Test
    fun `type # and check suggestion`() = runTest {
        composeTestRule.enterText("#")
        composeTestRule.shownViewWithText("#1")

        composeTestRule.clearText()
        composeTestRule.noShownSuggestions()
    }

    @Test
    fun `type @ and check suggestion`() = runTest {
        composeTestRule.enterText("@")
        composeTestRule.shownViewWithText("@1")

        composeTestRule.clearText()
        composeTestRule.noShownSuggestions()
    }

    @Test
    fun `click tag button and check suggestion`() = runTest(timeout = 1.seconds) {
        composeTestRule.clickHashTag()
        composeTestRule.shownViewWithText("#1")

        composeTestRule.clearText()
        composeTestRule.noShownSuggestions()
    }

    @Test
    fun `type mention button and check suggestion`() = runTest {
        composeTestRule.clickMention()
        composeTestRule.shownViewWithText("@1")

        composeTestRule.clearText()
        composeTestRule.noShownSuggestions()
    }
}
