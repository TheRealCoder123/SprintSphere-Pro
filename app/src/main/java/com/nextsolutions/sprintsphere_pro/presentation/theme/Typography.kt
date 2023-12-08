package com.upnext.notabox.presentation.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Typography(
    val description: TextStyle = TextStyle(
        fontSize = 10.sp
    ),
    val title: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    val small: TextStyle = TextStyle(
        fontSize = 10.sp
    )
 )
