package com.example.lebenindeutschland

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.lebenindeutschland.navigation.NavGraphSetup
import com.example.lebenindeutschland.screen.viewModel.QuizViewModel
import com.example.lebenindeutschland.ui.theme.LebenInDeutschlandTheme
import com.example.lebenindeutschland.ui.theme.color1
import com.example.lebenindeutschland.ui.theme.color2
import com.example.lebenindeutschland.ui.theme.color3
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LebenInDeutschlandTheme {
                val viewModel:QuizViewModel = hiltViewModel()
                // A surface container using the 'background' color from the theme

            Column(modifier = Modifier
                .fillMaxSize()
                .background(brushBackcgrounf)) {
                NavGraphSetup(navController = rememberNavController())
            }
            }
        }
    }
}

val brushBackcgrounf = Brush.linearGradient(listOf(color1, color2, color3))