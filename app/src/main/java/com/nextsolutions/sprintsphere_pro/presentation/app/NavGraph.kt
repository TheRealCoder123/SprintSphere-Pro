package com.nextsolutions.sprintsphere_pro.presentation.app

sealed class NavGraph(val route: String) {

    object HomeScreen : NavGraph("home_screen")
    object SetupScreen : NavGraph("setup_screen")
    object SettingsScreen : NavGraph("settings_screen")

}
