package com.nextsolutions.sprintsphere_pro.presentation.home_screen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.nextsolutions.sprintsphere_pro.presentation.app.NavGraph
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme
import com.nextsolutions.sprintsphere_pro.testing.SetupScreenTestTags
import kotlinx.coroutines.launch

@ExperimentalPermissionsApi
@Composable
fun PermissionDeniedContent(
    rationaleMessage: String,
    shouldShowRationale: Boolean,
    onRequestPermission: () -> Unit,
    onDenyPermission: () -> Unit
) {

    if (shouldShowRationale) {
        Dialog(
            onDismissRequest = { onDenyPermission() }
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(SprintSphereProTheme.colors.background)
                    .width(300.dp)
                    .padding(15.dp)
            ) {
                Text(
                    text = "Permission Request",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = SprintSphereProTheme.colors.text
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))


                Text(
                    text = rationaleMessage,
                    color = SprintSphereProTheme.colors.description
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {

                        androidx.compose.material3.Button(
                            onClick = {
                                onRequestPermission()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SprintSphereProTheme.colors.onBackground,
                            )
                        ) {
                            Text(text = "Give", color = SprintSphereProTheme.colors.onBackgroundText)
                        }

                        androidx.compose.material3.Button(
                            onClick = {
                                onDenyPermission()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SprintSphereProTheme.colors.onBackground,
                            )
                        ) {
                            Text(text = "Deny", color = SprintSphereProTheme.colors.onBackgroundText)
                        }

                    }
                }



            }
        }


    }

}

