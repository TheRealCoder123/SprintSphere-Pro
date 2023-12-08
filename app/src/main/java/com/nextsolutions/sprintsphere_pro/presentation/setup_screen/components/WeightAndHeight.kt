package com.nextsolutions.sprintsphere_pro.presentation.setup_screen.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.sprintsphere_pro.domain.utils.Gender
import com.nextsolutions.sprintsphere_pro.domain.utils.MetricAndImperialUnit
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme
import com.nextsolutions.sprintsphere_pro.testing.SetupScreenTestTags

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeightAndHeight(
    metricAndImperialUnit: MetricAndImperialUnit?,
    weight: String,
    height: String,
    onMetAndImpUnitSelected: (MetricAndImperialUnit) -> Unit,
    onWeightAdded: (String) -> Unit,
    onHeightAdded: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(20.dp)
            .testTag(SetupScreenTestTags.WeightAndHeight)
    ) {
        item {
            Text(
                text = "Please select your prefeared metric & imperial Unit?",
                color = SprintSphereProTheme.colors.text,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            FlowRow {

                PickerButton(
                    modifier = Modifier
                        .testTag(SetupScreenTestTags.MetricAndImperialKgCm),
                    isPicked = metricAndImperialUnit == MetricAndImperialUnit.KG_CM,
                    label = "Kg/cm"
                ) {
                    onMetAndImpUnitSelected(MetricAndImperialUnit.KG_CM)
                }

                Spacer(modifier = Modifier.width(15.dp))

                PickerButton(
                    modifier = Modifier
                        .testTag(SetupScreenTestTags.MetricAndImperialLbsFt),
                    isPicked = metricAndImperialUnit == MetricAndImperialUnit.LBS_FT,
                    label = "Lbs/ft"
                ) {
                    onMetAndImpUnitSelected(MetricAndImperialUnit.LBS_FT)
                }

            }

            Spacer(modifier = Modifier.height(30.dp))


        }

        item {
            Text(
                text = "Can you please enter your weight?",
                color = SprintSphereProTheme.colors.text,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            SetupTextField(
                value = weight,
                maxChars = 4,
                testTag = SetupScreenTestTags.WeightTextField,
                onValueChange = {
                    onWeightAdded(it)
                }
            )

            Spacer(modifier = Modifier.height(30.dp))


        }

        item {
            Text(
                text = "Can you please enter your height?",
                color = SprintSphereProTheme.colors.text,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            SetupTextField(
                value = height,
                maxChars = 4,
                testTag = SetupScreenTestTags.HeightTextField,
                onValueChange = {
                    onHeightAdded(it)
                }
            )

            Spacer(modifier = Modifier.height(30.dp))


        }

    }


}