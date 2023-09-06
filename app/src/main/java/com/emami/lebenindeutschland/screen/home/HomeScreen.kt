package com.emami.lebenindeutschland.screen.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.emami.lebenindeutschland.brushBackcgrounf
import com.emami.lebenindeutschland.screen.home.components.DropDownBund
import com.emami.lebenindeutschland.screen.home.components.Features
import com.emami.lebenindeutschland.screen.home.components.LinearProgress
import com.emami.lebenindeutschland.screen.viewModel.QuizViewModel
import com.emami.lebenindeutschland.ui.theme.Blau
import com.emami.lebenindeutschland.ui.theme.Blau2
import com.emami.lebenindeutschland.ui.theme.Green
import com.emami.lebenindeutschland.ui.theme.Green2
import com.emami.lebenindeutschland.ui.theme.Green3
import com.emami.lebenindeutschland.ui.theme.Red
import com.emami.lebenindeutschland.ui.theme.Red2
import com.emami.lebenindeutschland.ui.theme.Red3
import com.emami.lebenindeutschland.ui.theme.TextWhite
import com.emami.lebenindeutschland.ui.theme.color1
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current

    val viewModel = hiltViewModel<QuizViewModel>()

    Log.d("MYTAG", "view: ${viewModel.selectedBund.value}")



    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Home",
                    color = TextWhite,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium
                )
            }, colors = TopAppBarDefaults.smallTopAppBarColors(
                color1
            ), scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(canScroll = { true })
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(brushBackcgrounf)
        ) {
            val valu2 = 60
            val valu3 = 40
            var value1 by remember { mutableStateOf(0) }
            var value2 by remember { mutableStateOf(0) }
            var value3 by remember { mutableStateOf(0) }

            LaunchedEffect(key1 = Unit) {

                delay(100)
                value1 =
                    viewModel.allQuizFromDB.count { it.isCorOrNot == false || it.isCorOrNot == true }
                value2 = viewModel.allQuizFromDB.count { it.isCorOrNot == true }
                value3 = viewModel.allQuizFromDB.count { it.isCorOrNot == false }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
            ) {
                LinearProgress(value1, 310, gradientColors = listOf(Blau2, Blau, Blau2))
                LinearProgress(value2, 300, gradientColors = listOf(Green2, Green, Green3))
                LinearProgress(value3, 310, gradientColors = listOf(Red, Red2, Red3))
            }
            Spacer(modifier = Modifier.height(30.dp))

            DropDownBund(viewModel = viewModel, context)
            Spacer(modifier = Modifier.height(5.dp))

            Features(navController)

        }

    }

}