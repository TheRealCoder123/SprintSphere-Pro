package com.nextsolutions.sprintsphere_pro.presentation.setup_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.nextsolutions.sprintsphere_pro.presentation.app.NavGraph
import com.nextsolutions.sprintsphere_pro.presentation.setup_screen.components.GenderAndAge
import com.nextsolutions.sprintsphere_pro.presentation.setup_screen.components.WeightAndHeight
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme
import com.nextsolutions.sprintsphere_pro.testing.SetupScreenTestTags
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetupScreen(
   navController: NavController,
   viewModel: SetupViewModel = hiltViewModel(),
) {

   val context = LocalContext.current

   val pagerState = rememberPagerState()
   val scope = rememberCoroutineScope()

   Scaffold(
      backgroundColor = SprintSphereProTheme.colors.background,
      topBar = {
         Box(modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp), contentAlignment = Alignment.Center
         ){
            HorizontalPagerIndicator(
               pagerState = pagerState,
               activeColor = SprintSphereProTheme.colors.iconTint,
               inactiveColor = SprintSphereProTheme.colors.description,
               indicatorHeight = 10.dp,
               indicatorWidth = 10.dp
            )
         }
      },
      bottomBar = {

         Box(modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
         ){

            Button(
               modifier = Modifier
                  .testTag(SetupScreenTestTags.DoneButton)
                  .fillMaxWidth()
                  .padding(10.dp),
               onClick = {

                  if (pagerState.currentPage == 0){
                     scope.launch {
                        pagerState.animateScrollToPage(1, 1f)
                     }
                  }else{

                     if (viewModel.ageState.isEmpty()) {
                        Toast.makeText(context, "Please enter your age", Toast.LENGTH_SHORT).show()
                        return@Button
                     }

                     if (viewModel.genderState == null){
                        Toast.makeText(context, "Please select you gender", Toast.LENGTH_SHORT).show()
                        return@Button
                     }

                     if (viewModel.metricAndImperialUnit == null) {
                        Toast.makeText(
                           context,
                           "Please select your preferred metric & imperial Unit",
                           Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                     }

                     if (viewModel.weightState.isEmpty()){
                        Toast.makeText(context, "Please enter your weight", Toast.LENGTH_SHORT).show()
                        return@Button
                     }

                     if (viewModel.heightState.isEmpty()){
                        Toast.makeText(context, "Please enter your height", Toast.LENGTH_SHORT).show()
                        return@Button
                     }


                     viewModel.saveDetails()
                     viewModel.updateSetupState(true)
                     navController.popBackStack()
                     navController.navigate(NavGraph.HomeScreen.route)
                  }
               },
               colors = ButtonDefaults.buttonColors(
                  containerColor = SprintSphereProTheme.colors.onBackground,
                  contentColor = SprintSphereProTheme.colors.onBackgroundText
               )
            ) {

               Text(
                  text = when(pagerState.currentPage){
                     0 -> "Next"
                     else -> "Done"
                  }
               )

            }

         }


      }
   ) {

      Column(
         modifier = Modifier
            .padding(it)
            .fillMaxSize()
      ) {

         HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = 2,
            state = pagerState,
            verticalAlignment = Alignment.Top
         ) { pageId ->
            when(pageId){
               0 -> {
                  GenderAndAge(
                     onAgeAdded = { newAge ->
                        viewModel.ageState = newAge
                     },
                     onGenderSelected = { newGender ->
                         viewModel.genderState = newGender
                     },
                     age = viewModel.ageState,
                     gender = viewModel.genderState
                  )
               }
               1 -> {
                  WeightAndHeight(
                     viewModel.metricAndImperialUnit,
                     viewModel.weightState,
                     viewModel.heightState,
                     onMetAndImpUnitSelected = { newMetricAndImpUnit ->
                        viewModel.metricAndImperialUnit = newMetricAndImpUnit
                     },
                     onWeightAdded = { newWeight ->
                        viewModel.weightState = newWeight
                     },
                     onHeightAdded = { newHeight ->
                        viewModel.heightState = newHeight
                     }
                  )
               }
            }
         }

      }

   }

}