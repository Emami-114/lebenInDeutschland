package com.emami.lebenindeutschland.screen.quizScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.emami.lebenindeutschland.brushBackcgrounf
import com.emami.lebenindeutschland.domain.model.QuizModel
import com.emami.lebenindeutschland.navigation.ScreenReoute
import com.emami.lebenindeutschland.screen.home.components.LinearProgress2
import com.emami.lebenindeutschland.screen.quizScreen.componenten.AnswerItem
import com.emami.lebenindeutschland.screen.quizScreen.componenten.noRippleClickable
import com.emami.lebenindeutschland.screen.viewModel.QuizViewModel
import com.emami.lebenindeutschland.ui.theme.Blau
import com.emami.lebenindeutschland.ui.theme.Green
import com.emami.lebenindeutschland.ui.theme.Red
import com.emami.lebenindeutschland.ui.theme.TextWhite
import com.emami.lebenindeutschland.ui.theme.color1
import com.emami.lebenindeutschland.ui.theme.color3
import com.emami.lebenindeutschland.ui.theme.color6
import kotlinx.coroutines.delay

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestQuizScreen(navController: NavController) {

    val viewModel: QuizViewModel = hiltViewModel()

    val allQuizFilter = viewModel.allQuizFromDB.shuffled().take(33)

    val quizList = allQuizFilter

    var openDialog by remember { mutableStateOf(false) }


    val quizIndex = viewModel.quizIndex

    val currentQuiz = try {
        quizList[viewModel.quizIndex.value % quizList.size]
    } catch (e: Exception) {
        null
    } ?: QuizModel()

    val answers = listOf(
        currentQuiz.answer1, currentQuiz.answer2, currentQuiz.answer3, currentQuiz.corAnswer
    ).shuffled()

    val quizTestResult = viewModel.scoreResult


    val deMarket = mutableStateOf(
        currentQuiz.isFavors ?: false
    )

    Log.d("MYTAG", viewModel.scoreResult.value.toString())

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

                QuizTestItem(
                    viewModel = viewModel,
                    quiz = currentQuiz,
                    totalQuiz = quizList.size,
                    answerList = answers,
                    quizIndex = quizIndex.value,
                    testQuizResult = quizTestResult,
                    paddingValues = paddingValue
                ) {
                }

                AnimatedVisibility(visible = openDialog) {
                    AlertDialog(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onDismissRequest = { openDialog = false },
                        title = {

                        },
                        text = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Spacer(modifier = Modifier.height(5.dp))
//
                                Text(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    text = if (quizTestResult.intValue > 17) "Sie haben bestanden" else "Sie haben nicht bestanden!",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 28.sp,
                                    color = if (quizTestResult.intValue > 17) Green else Red
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = if (quizTestResult.intValue > 17) "Sie haben von 33 Fragen ${quizTestResult.intValue} richtig beantwortet. "
                                    else "Sie haben von 33 Fragen nur ${quizTestResult.intValue} richtig beantwortet. Sie müssen mindestens 17 Fragen richtig beantworten. Üben Sie mehr und machen Sie den Test erneut.",
                                    fontSize = 16.sp
                                )


                            }


                        },
                        confirmButton = {
                            Button(contentPadding = PaddingValues(
                                vertical = 5.dp,
                                horizontal = 10.dp
                            ),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = color3),
                                onClick = {
//                                navController.navigate(DetailsScreen.QuizDisplayCounter.route)
                                    viewModel.quizIndex.value = 0
                                    quizTestResult.intValue = 0
                                    openDialog = false
                                }) {

                                Text(
                                    text = "Wiederholen",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    navController.navigate(ScreenReoute.Home.route)
                                    openDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(
                                    top = 5.dp, bottom = 5.dp, start = 12.dp, end = 12.dp
                                ),
                                border = BorderStroke(.9.dp, color1)
                            ) {
                                Text(
                                    text = "Zurück",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium
                                )

                            }
                        },
                        containerColor = TextWhite,
                        shape = RoundedCornerShape(10.dp),
                        properties = DialogProperties(dismissOnClickOutside = false)

                    )
                }


            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brushBackcgrounf),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Keine Fragen vorhanden :(", color = TextWhite)

                }
            }


        }
        if (quizIndex.value > quizList.size - 1 && quizList.isNotEmpty()) {
            openDialog = true
        }

    }


}


@SuppressLint("UnrememberedMutableState")
@Composable
private fun QuizTestItem(
    viewModel: QuizViewModel = hiltViewModel(),
    quiz: QuizModel,
    totalQuiz: Int?,
    answerList: List<String>,
    quizIndex: Int,
    testQuizResult: MutableState<Int>,
    paddingValues: PaddingValues,
    onClick: () -> Unit
) {
    val answerIndex = remember { mutableIntStateOf(0) }
    val correctOrNot = remember { mutableStateOf<Boolean?>(null) }

    var isSelectedActive by remember { mutableStateOf(false) }

    val updateAnswer: (Int) -> Unit = remember(quiz) {
        {
            answerIndex.value = it
            correctOrNot.value = answerList[it] == quiz.corAnswer
        }
    }


    val correctAnswerIndex = answerList.indexOf(quiz.corAnswer)
    val isAnswered = correctOrNot.value != null
    val totalQuiz2 = totalQuiz ?: 0


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brushBackcgrounf)
            .verticalScroll(state = rememberScrollState())
            .padding(paddingValues)
            .padding(horizontal = 5.dp)
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        if (totalQuiz != null) {
            LinearProgress2(quizIndex, totalQuiz - 1)
        }

        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = quiz.question,
            fontSize = 20.sp,
            color = TextWhite,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Medium
        )

        quiz.url?.let {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(180.dp),
                painter = painterResource(id = it),
                contentDescription = null,
            )
        }



        Spacer(modifier = Modifier.height(10.dp))

        answerList.forEachIndexed { index, s ->
            AnswerItem(
                answer = s,
                icons = if (correctOrNot.value != null && index == answerIndex.value) Icons.Rounded.TaskAlt else Icons.Outlined.Circle,

                color = if (correctOrNot.value != null && answerIndex.value == index) Blau else TextWhite,

                borderColor = if (correctOrNot.value != null && index == answerIndex.value) Blau.copy(
                    alpha = .6f
                ) else Color.Gray,
                isActiveState = isSelectedActive, fontSize = 20.sp
            ) {
                isSelectedActive = true
                updateAnswer.invoke(index)
                if (correctOrNot.value != null) {
                    if (correctOrNot.value == true) {
//                        testQuizResult.value +1
                        viewModel.updateIsCoreorNot(quiz.id, true)
                    } else {
                        viewModel.updateIsCoreorNot(quiz.id, true)

                    }

                }
            }
        }


        nextBtn(corOrNot = correctOrNot.value, index = viewModel.quizIndex) {
            if (correctOrNot.value == true) {
                testQuizResult.value = testQuizResult.value + 1
            }
            isSelectedActive = false
            correctOrNot.value = null
            onClick()
        }

    }

}


@Composable
fun nextBtn(corOrNot: Boolean?, index: MutableState<Int>, onClick: () -> Unit) {
    LaunchedEffect(key1 = corOrNot) {
        if (corOrNot != null) {
            delay(1500)
            index.value = index.value + 1
            onClick()
        }
    }
}


@Composable
private fun AnswerItem2(
    answer: String,
    icons: ImageVector,
    color: Color,
    borderColor: Color,
    isActiveState: Boolean,
    fontSize: TextUnit = 22.sp,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                2.dp, color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .noRippleClickable { if (!isActiveState) onClick.invoke() }
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                modifier = Modifier.weight(9f),
                fontSize = fontSize,
                fontWeight = FontWeight.Normal,
                text = answer, color = color
            )
            Icon(
                imageVector = icons, contentDescription = null, tint = color,
                modifier = Modifier
                    .size(28.dp)
                    .weight(1f)
                    .clickable { if (!isActiveState) onClick.invoke() }
            )

        }

    }

}
