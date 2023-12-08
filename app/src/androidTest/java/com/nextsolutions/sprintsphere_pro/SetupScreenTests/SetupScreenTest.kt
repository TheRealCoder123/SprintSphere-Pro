package com.nextsolutions.sprintsphere_pro.SetupScreenTests

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nextsolutions.sprintsphere_pro.di.HiltModule
import com.nextsolutions.sprintsphere_pro.presentation.app.Application
import com.nextsolutions.sprintsphere_pro.presentation.app.MainActivity
import com.nextsolutions.sprintsphere_pro.presentation.app.NavGraph
import com.nextsolutions.sprintsphere_pro.presentation.home_screen.HomeScreen
import com.nextsolutions.sprintsphere_pro.presentation.settings_screen.SettingsScreen
import com.nextsolutions.sprintsphere_pro.presentation.setup_screen.SetupScreen
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme
import com.nextsolutions.sprintsphere_pro.testing.SetupScreenTestTags
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(HiltModule::class)
class SetupScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun setup() {

        hiltRule.inject()
        composeRule.activity.setContent {
            val navHostController = rememberAnimatedNavController()
            AnimatedNavHost(
                navController = navHostController,
                startDestination = NavGraph.SetupScreen.route,

            ){
                composable(NavGraph.SetupScreen.route){
                    SetupScreen(navHostController)
                }
                composable(NavGraph.HomeScreen.route){}
            }
        }
        composeRule.waitForIdle()

    }


    @Test
    fun test_setup_onboard_male_and_km_cm() {

        composeRule.onNodeWithTag(SetupScreenTestTags.GenderAndAge).assertIsDisplayed()

        composeRule.onNodeWithTag(SetupScreenTestTags.AgeTextField).performTextInput("18")
        composeRule.onNodeWithTag(SetupScreenTestTags.MaleGender).performClick()

        composeRule.onNodeWithTag(SetupScreenTestTags.DoneButton).performClick()

        composeRule.onNodeWithTag(SetupScreenTestTags.WeightAndHeight).assertIsDisplayed()

        composeRule.onNodeWithTag(SetupScreenTestTags.MetricAndImperialKgCm).performClick()
        composeRule.onNodeWithTag(SetupScreenTestTags.WeightTextField).performTextInput("60")
        composeRule.onNodeWithTag(SetupScreenTestTags.HeightTextField).performTextInput("180")

        composeRule.onNodeWithTag(SetupScreenTestTags.DoneButton).performClick()

    }

    @Test
    fun test_setup_onboard_female_and_lbs_ft() {

        composeRule.onNodeWithTag(SetupScreenTestTags.GenderAndAge).assertIsDisplayed()

        composeRule.onNodeWithTag(SetupScreenTestTags.AgeTextField).performTextInput("45")
        composeRule.onNodeWithTag(SetupScreenTestTags.FemaleGender).performClick()

        composeRule.onNodeWithTag(SetupScreenTestTags.DoneButton).performClick()

        composeRule.onNodeWithTag(SetupScreenTestTags.WeightAndHeight).assertIsDisplayed()

        composeRule.onNodeWithTag(SetupScreenTestTags.MetricAndImperialLbsFt).performClick()
        composeRule.onNodeWithTag(SetupScreenTestTags.WeightTextField).performTextInput("80")
        composeRule.onNodeWithTag(SetupScreenTestTags.HeightTextField).performTextInput("190")

        composeRule.onNodeWithTag(SetupScreenTestTags.DoneButton).performClick()

    }

    @Test
    fun test_setup_onboard_other_gender() {

        composeRule.onNodeWithTag(SetupScreenTestTags.GenderAndAge).assertIsDisplayed()

        composeRule.onNodeWithTag(SetupScreenTestTags.AgeTextField).performTextInput("22")
        composeRule.onNodeWithTag(SetupScreenTestTags.OtherGender).performClick()

        composeRule.onNodeWithTag(SetupScreenTestTags.DoneButton).performClick()

        composeRule.onNodeWithTag(SetupScreenTestTags.WeightAndHeight).assertIsDisplayed()

        composeRule.onNodeWithTag(SetupScreenTestTags.MetricAndImperialLbsFt).performClick()
        composeRule.onNodeWithTag(SetupScreenTestTags.WeightTextField).performTextInput("65")
        composeRule.onNodeWithTag(SetupScreenTestTags.HeightTextField).performTextInput("170")

        composeRule.onNodeWithTag(SetupScreenTestTags.DoneButton).performClick()

    }




}