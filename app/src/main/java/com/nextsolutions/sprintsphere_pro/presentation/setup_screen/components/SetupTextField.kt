package com.nextsolutions.sprintsphere_pro.presentation.setup_screen.components

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme
import com.nextsolutions.sprintsphere_pro.testing.SetupScreenTestTags

@Composable
fun SetupTextField(
    modifier: Modifier = Modifier,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Number,
    maxChars: Int = 3,
    testTag: String,
    onValueChange: (String) -> Unit
) {


    Box(
        modifier = modifier
            .border(1.dp, SprintSphereProTheme.colors.iconTint, RoundedCornerShape(10.dp))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ){
        BasicTextField(
            modifier = Modifier.testTag(testTag),
            value = value,
            onValueChange = {
                val numericPart = it.replace("\\D".toRegex(), "")
                val numericPartWithoutLeadingZeros = numericPart.replace("^0+".toRegex(), "")

                if (numericPartWithoutLeadingZeros.length > maxChars) {
                    return@BasicTextField
                } else {
                    onValueChange(it)
                }
            },
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                color = SprintSphereProTheme.colors.text,
                fontSize = 17.sp
            ),
            decorationBox = {
                if (value.isEmpty()){
                    Text(
                        modifier = Modifier
                            .testTag(SetupScreenTestTags.XXXTextFieldLabel)
                            .align(Alignment.Center),
                        text = "XXX",
                        color = Color.Gray,
                        fontSize = 17.sp
                    )
                }
                it()
            },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }




}

@Preview
@Composable
fun SetupTextFieldPrev() {
    SetupTextField(value = "", testTag = SetupScreenTestTags.AgeTextField) {}
}