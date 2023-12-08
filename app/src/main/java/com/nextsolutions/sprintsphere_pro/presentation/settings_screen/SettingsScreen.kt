package com.nextsolutions.sprintsphere_pro.presentation.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme

@Composable
fun SettingsScreen() {

    Box(modifier = Modifier.fillMaxSize().background(SprintSphereProTheme.colors.background), contentAlignment = Alignment.Center){
        Text(text = "Settings Screen", color = SprintSphereProTheme.colors.text)
    }

}