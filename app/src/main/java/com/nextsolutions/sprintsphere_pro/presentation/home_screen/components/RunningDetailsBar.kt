package com.nextsolutions.sprintsphere_pro.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SocialDistance
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme

@Composable
fun RunningDetailsBar(
    time: String,
    distance: String,
    speed: String,
    calories: String
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SprintSphereProTheme.colors.background)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {


        Text(
            text = time,
            color = SprintSphereProTheme.colors.text,
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            
            RunningItem(
                name = "Distance",
                icon = Icons.Default.SocialDistance,
                value = distance,
                unit = "km"
            )

            RunningItem(
                name = "Speed",
                icon = Icons.Default.Speed,
                value = speed,
                unit = "km/h"
            )

            RunningItem(
                name = "Calories",
                icon = Icons.Default.LocalFireDepartment,
                value = calories,
                unit = "kcal"
            )

            
        }


    }



}

@Composable
fun RunningItem(
    name: String,
    icon: ImageVector,
    value: String,
    unit: String
) {

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = name,
                color = SprintSphereProTheme.colors.text
            )

            Icon(
                imageVector = icon,
                contentDescription = name,
                tint = SprintSphereProTheme.colors.iconTint
            )

        }

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        color = SprintSphereProTheme.colors.text
                    )
                ) {
                    append(value)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        color = SprintSphereProTheme.colors.text
                    )
                ) {
                    append(" $unit")
                }
            },
            color = SprintSphereProTheme.colors.text
        )

    }

}

@Preview
@Composable
fun RunningDetailsBarPreview() {
    RunningDetailsBar("00:00:00","0.0","0.0","0.0")
}