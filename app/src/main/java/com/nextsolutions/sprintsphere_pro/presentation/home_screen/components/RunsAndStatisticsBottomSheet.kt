package com.nextsolutions.sprintsphere_pro.presentation.home_screen.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme

@Composable
fun RunsAndStatisticsBottomSheet() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        item {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp), contentAlignment = Alignment.Center){
                Spacer(modifier = Modifier
                    .width(60.dp)
                    .height(2.dp)
                    .background(Color.Gray)
                    .clip(RoundedCornerShape(10.dp))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Runs & Statistics",
                    color = SprintSphereProTheme.colors.text
                )

                Icon(
                    imageVector = Icons.Default.QueryStats,
                    contentDescription = "Statistics",
                    tint = SprintSphereProTheme.colors.iconTint
                )

            }

        }

    }


}