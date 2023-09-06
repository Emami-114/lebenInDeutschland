package com.emami.lebenindeutschland.screen.quizScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.emami.lebenindeutschland.brushBackcgrounf
import com.emami.lebenindeutschland.domain.model.QuizModel
import com.emami.lebenindeutschland.screen.quizScreen.componenten.QuizItem
import com.emami.lebenindeutschland.screen.quizScreen.componenten.noRippleClickable
import com.emami.lebenindeutschland.screen.viewModel.QuizViewModel
import com.emami.lebenindeutschland.ui.theme.TextWhite
import com.emami.lebenindeutschland.ui.theme.color1

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun AllQuizScreen(navController: NavController) {
    val viewModel: QuizViewModel = hiltViewModel()

    var quizList = viewModel.allQuizFromDB.shuffled().toMutableList()




    val quizIndex = viewModel.quizIndex

    val currentQuiz = try {
        quizList[viewModel.quizIndex.value % quizList.size]
    } catch (e: Exception) {
        null
    } ?: QuizModel()

    val answers = listOf(
        currentQuiz.answer1, currentQuiz.answer2, currentQuiz.answer3, currentQuiz.corAnswer
    ).shuffled()


    val deMarket = mutableStateOf(
        currentQuiz.isFavors ?: false
    )

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = color1),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TopAppBar(
                modifier = Modifier.weight(8f),
                title = {
                    Text(
                        text = "Zurück",
                        color = TextWhite,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .noRippleClickable {
                                navController.navigateUp()
                            }
                            .size(30.dp),
                        tint = TextWhite
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = color1),
            )
            IconButton(modifier = Modifier.weight(2f), onClick = {
                deMarket.value = !deMarket.value
                viewModel.updateIsFavorite(currentQuiz.id, deMarket.value)
            }) {
                Icon(
                    imageVector = if (deMarket.value) Icons.Filled.Bookmarks else Icons.Outlined.Bookmarks,
                    contentDescription = null,
                    tint = TextWhite,
                    modifier = Modifier.size(30.dp)
                )
            }

        }
    }) { paddingValue ->

        Column {


            if (!quizList.isNullOrEmpty()) {

                QuizItem(
                    viewModel = viewModel,
                    quiz = currentQuiz,
                    totalQuiz = quizList.size,
                    answerList = answers,
                    quizIndex = quizIndex.value,
                    paddingValues = paddingValue
                ) {


                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brushBackcgrounf),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Keine Letzter Fehler vorhanden :(", color = TextWhite)

                }
            }
        }
        if (quizIndex.value > quizList.size - 1 && quizList.isNotEmpty()) {
            navController.navigateUp()
        }

    }


}