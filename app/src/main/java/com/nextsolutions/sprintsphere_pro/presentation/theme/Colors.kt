package com.nextsolutions.x_o.presentation.theme

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class ColorScheme(
    primary: Color,
    iconTint: Color,
    background: Color,
    onBackground: Color,
    onBackgroundText: Color,
    onBackgroundIconTint: Color,
    secondary: Color,
    text: Color,
    description: Color,
    selected: Color,
    onSelected: Color,
    unSelected: Color,
    onUnSelected: Color,
) {

    var primary by mutableStateOf(primary)
        private set

    var iconTint by mutableStateOf(iconTint)
        private set

    var onBackground by mutableStateOf(onBackground)
        private set

    var background by mutableStateOf(background)
        private set

    var onBackgroundText by mutableStateOf(onBackgroundText)
        private set

    var onBackgroundIconTint by mutableStateOf(onBackgroundIconTint)
        private set

    var secondary by mutableStateOf(secondary)
        private set

    var text by mutableStateOf(text)
        private set

    var description by mutableStateOf(description)
        private set

    var selected by mutableStateOf(selected)
        private set

    var onSelected by mutableStateOf(onSelected)
        private set

    var unSelected by mutableStateOf(unSelected)
        private set

    var onUnSelected by mutableStateOf(onUnSelected)
        private set


    fun copy(
        primary: Color = this.primary,
        iconTint: Color = this.iconTint,
        background: Color = this.background,
        onBackground: Color = this.onBackground,
        onBackgroundText: Color = this.onBackgroundText,
        onBackgroundIconTint: Color = this.onBackgroundIconTint,
        secondary: Color = this.secondary,
        text: Color = this.text,
        description: Color = this.description,
        selected: Color = this.selected,
        onSelected: Color = this.onSelected,
        unSelected: Color = this.unSelected,
        onUnSelected: Color = this.onUnSelected,
    ) = ColorScheme(
        primary = primary,
        iconTint = iconTint,
        background = background,
        onBackground = onBackground,
        onBackgroundText = onBackgroundText,
        onBackgroundIconTint = onBackgroundIconTint,
        secondary = secondary,
        text = text,
        description = description,
        selected = selected,
        onSelected = onSelected,
        unSelected = unSelected,
        onUnSelected = onUnSelected,
    )

    fun updateColorsFrom(other: ColorScheme) {
        primary = other.primary
        iconTint = other.iconTint
        background = other.background
        onBackground = other.onBackground
        onBackgroundText = other.onBackgroundText
        onBackgroundIconTint = other.onBackgroundIconTint
        secondary = other.secondary
        text = other.text
        description = other.description
        selected = other.selected
        onSelected = other.onSelected
        unSelected = other.unSelected
        onUnSelected = other.onUnSelected
    }

}


fun lightColors(
    primary: Color,
    iconTint: Color,
    background: Color,
    onBackground: Color,
    onBackgroundText: Color,
    onBackgroundIconTint: Color,
    secondary: Color,
    text: Color,
    description: Color,
    selected: Color,
    onSelected: Color,
    unSelected: Color,
    onUnSelected: Color,
) = ColorScheme (
    primary,
    iconTint,
    background,
    onBackground,
    onBackgroundText,
    onBackgroundIconTint,
    secondary,
    text,
    description,
    selected,
    onSelected,
    unSelected,
    onUnSelected,
)

fun darkColors(
    primary: Color,
    iconTint: Color,
    background: Color,
    onBackground: Color,
    onBackgroundText: Color,
    onBackgroundIconTint: Color,
    secondary: Color,
    text: Color,
    description: Color,
    selected: Color,
    onSelected: Color,
    unSelected: Color,
    onUnSelected: Color,
) = ColorScheme(
    primary,
    iconTint,
    background,
    onBackground,
    onBackgroundText,
    onBackgroundIconTint,
    secondary,
    text,
    description,
    selected,
    onSelected,
    unSelected,
    onUnSelected,
)

