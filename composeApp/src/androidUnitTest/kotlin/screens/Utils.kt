@file:OptIn(ExperimentalTestApi::class)

package com.me.screens

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput

fun AndroidComposeTestRule<*, *>.newRecordIsAddedWithText(text: String) =
    this.onNode(hasText(text))
        .assertExists()

@OptIn(ExperimentalTestApi::class)
fun AndroidComposeTestRule<*, *>.shownViewWithText(text: String) =
    waitUntilAtLeastOneExists(hasText(text))

fun AndroidComposeTestRule<*, *>.noViewWithText(text: String) =
    this.onNode(hasText(text))
        .assertDoesNotExist()

fun AndroidComposeTestRule<*, *>.noShownSuggestions() =
    this.onNode(hasTestTag("suggestion"))
        .assertIsNotDisplayed()

fun AndroidComposeTestRule<*, *>.newRecordIsNotAdded() =
    this.onNode(hasTestTag("record card"))
        .assertDoesNotExist()

fun AndroidComposeTestRule<*, *>.recordWithNoTextIsAdded() =
    this.onNode(hasTestTag("record card"))
        .assertExists()

fun AndroidComposeTestRule<*, *>.clickOk() =
    this.onNode(hasTestTag("ok button"))
        .performClick()

fun AndroidComposeTestRule<*, *>.clickHashTag() =
    this.onNode(hasTestTag("hashtag button"))
        .assertExists()
        .performClick()

fun AndroidComposeTestRule<*, *>.clickMention() =
    this.onNode(hasTestTag("mention button"))
        .assertExists()
        .performClick()

fun AndroidComposeTestRule<*, *>.clickCancel() =
    this.onNode(hasTestTag("cancel button"))
        .performClick()

fun AndroidComposeTestRule<*, *>.smallWheelHasSelection() =
    this.onNode(hasTestTag("small wheel"))
        .assertExists()

fun AndroidComposeTestRule<*, *>.smallWheelHasNoSelection() =
    this.onNode(hasTestTag("small wheel"))
        .assertDoesNotExist()

fun AndroidComposeTestRule<*, *>.bigWheelIsShown() =
    this.onNode(hasTestTag("big wheel"))
        .assertExists()

fun AndroidComposeTestRule<*, *>.bigWheelIsHidden() =
    this.onNode(hasTestTag("big wheel"))
        .assertDoesNotExist()


fun AndroidComposeTestRule<*, *>.clickBigWheel() =
    this.onNode(hasTestTag("today screen root"))
        .apply {
            performTouchInput {
                down(bottomRight - Offset(50f, 400f))
                up()
            }
        }

fun AndroidComposeTestRule<*, *>.clickSmallWheel() =
    this.onNode(hasTestTag("empty small wheel"))
        .performClick()


fun AndroidComposeTestRule<*, *>.enterText(text: String) =
    this.onNode(hasTestTag("text input")).apply {
        assertExists()
        performTextInput(text)
    }

fun AndroidComposeTestRule<*, *>.clearText() =
    this.onNode(hasTestTag("text input")).apply {
        assertExists()
        performTextClearance()
    }

fun AndroidComposeTestRule<*, *>.inputFieldHasText(text: String) =
    this.onNode(hasTestTag("text input")).apply {
        assertExists()
        assertTextEquals(text)
    }

fun AndroidComposeTestRule<*, *>.elementPresented(tag: String) =
    this.onNode(hasTestTag(tag)).let {
        it.assertExists()
        it.assertIsEnabled()
    }
