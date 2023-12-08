package com.nextsolutions.sprintsphere_pro.presentation.setup_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.sprintsphere_pro.domain.utils.Gender
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme
import com.nextsolutions.sprintsphere_pro.testing.SetupScreenTestTags

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenderAndAge(
    onAgeAdded: (String) -> Unit,
    onGenderSelected: (Gender) -> Unit,
    age: String,
    gender: Gender?
) {



    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(20.dp)
            .testTag(SetupScreenTestTags.GenderAndAge)
    ) {

        item {
            Text(
                text = "Can you please enter your age?",
                color = SprintSphereProTheme.colors.text,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            SetupTextField(
                value = age,
                testTag = SetupScreenTestTags.AgeTextField,
                onValueChange = {
                    onAgeAdded(it)
                }
            )

            Spacer(modifier = Modifier.height(30.dp))


        }

        item {
            Text(
                text = "Can you please select your gender?",
                color = SprintSphereProTheme.colors.text,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            FlowRow {

                PickerButton(
                    modifier = Modifier
                        .testTag(SetupScreenTestTags.MaleGender),
                    isPicked = gender == Gender.Male,
                    label = "Male"
                ) {
                    onGenderSelected(Gender.Male)
                }

                Spacer(modifier = Modifier.width(15.dp))

                PickerButton(
                    modifier = Modifier
                        .testTag(SetupScreenTestTags.FemaleGender),
                    isPicked = gender == Gender.Female,
                    label = "Female"
                ) {
                    onGenderSelected(Gender.Female)
                }

                Spacer(modifier = Modifier.width(15.dp))

                PickerButton(
                    modifier = Modifier
                        .testTag(SetupScreenTestTags.OtherGender),
                    isPicked = gender == Gender.Other,
                    label = "Other"
                ) {
                    onGenderSelected(Gender.Other)
                }


            }

        }

    }


}