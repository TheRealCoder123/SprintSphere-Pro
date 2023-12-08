package com.nextsolutions.sprintsphere_pro.presentation.setup_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme

@Composable
fun PickerButton(
    modifier: Modifier = Modifier,
    isPicked: Boolean,
    label: String,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, SprintSphereProTheme.colors.onBackground, RoundedCornerShape(10.dp))
            .background(if (isPicked) SprintSphereProTheme.colors.selected else SprintSphereProTheme.colors.unSelected)
            .clickable { onClick() }
            .padding(horizontal = 30.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ){

        Text(
            text = label,
            color = if (isPicked) SprintSphereProTheme.colors.onSelected else SprintSphereProTheme.colors.onUnSelected
        )

    }

}