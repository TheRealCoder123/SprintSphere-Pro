package com.nextsolutions.sprintsphere_pro.SetupScreenTests

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nextsolutions.sprintsphere_pro.di.HiltModule
import com.nextsolutions.sprintsphere_pro.presentation.app.MainActivity
import com.nextsolutions.sprintsphere_pro.presentation.app.NavGraph
import com.nextsolutions.sprintsphere_pro.presentation.setup_screen.SetupScreen
import com.nextsolutions.sprintsphere_pro.presentation.setup_screen.components.SetupTextField
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme
import com.nextsolutions.sprintsphere_pro.testing.SetupScreenTestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(HiltModule::class)
class SetupTextFieldTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.activity.setContent {
            var textFieldState by remember {
                mutableStateOf("")
            }
            SprintSphereProTheme {
                SetupTextField(
                    value = textFieldState,
                    testTag = SetupScreenTestTags.AgeTextField,
                    maxChars = 4,
                    onValueChange = {
                        textFieldState = it
                    }
                )
            }
        }
        composeRule.waitForIdle()
    }

    @Test
    fun test_setup_text_field_whole_number() {


        composeRule.onNodeWithTag(SetupScreenTestTags.XXXTextFieldLabel, useUnmergedTree = true).assertIsDisplayed()

        composeRule.onNodeWithTag(SetupScreenTestTags.AgeTextField).performTextInput("30")

        composeRule.onNodeWithTag(SetupScreenTestTags.XXXTextFieldLabel, useUnmergedTree = true).assertDoesNotExist()

        composeRule.onNodeWithTag(SetupScreenTestTags.AgeTextField).performTextClearance()

        composeRule.onNodeWithTag(SetupScreenTestTags.XXXTextFieldLabel, useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun test_setup_text_field_decimal_number() {


        composeRule.onNodeWithTag(SetupScreenTestTags.XXXTextFieldLabel, useUnmergedTree = true).assertIsDisplayed()

        composeRule.onNodeWithTag(SetupScreenTestTags.AgeTextField).performTextInput("920.8")

        composeRule.onNodeWithTag(SetupScreenTestTags.XXXTextFieldLabel, useUnmergedTree = true).assertDoesNotExist()

        composeRule.onNodeWithTag(SetupScreenTestTags.AgeTextField).performTextClearance()

        composeRule.onNodeWithTag(SetupScreenTestTags.XXXTextFieldLabel, useUnmergedTree = true).assertIsDisplayed()

    }








}