package com.nextsolutions.sprintsphere_pro.presentation.app

import android.app.Application
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nextsolutions.sprintsphere_pro.presentation.home_screen.HomeScreen
import com.nextsolutions.sprintsphere_pro.presentation.settings_screen.SettingsScreen
import com.nextsolutions.sprintsphere_pro.presentation.setup_screen.SetupScreen
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Application() {
    SprintSphereProTheme {

        val globalViewModel: GlobalViewModel = hiltViewModel()

        val navHostController = rememberAnimatedNavController()

        AnimatedNavHost(
            navController = navHostController,
            startDestination = if (globalViewModel.isSetupFinished) NavGraph.HomeScreen.route else NavGraph.SetupScreen.route,
//            enterTransition = { enterTransition() },
//            exitTransition = { exitTransition() },
//            popEnterTransition = { popEnterTransition() },
//            popExitTransition = { popExitTransition() }
        ){

            composable(NavGraph.HomeScreen.route){
                HomeScreen()
            }

            composable(NavGraph.SetupScreen.route){
                SetupScreen(navHostController)
            }

            composable(NavGraph.SettingsScreen.route){
                SettingsScreen()
            }

        }


    }
}