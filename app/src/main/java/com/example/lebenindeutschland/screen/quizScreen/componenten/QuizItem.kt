package com.example.lebenindeutschland.screen.quizScreen.componenten

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lebenindeutschland.R
import com.example.lebenindeutschland.brushBackcgrounf
import com.example.lebenindeutschland.domain.model.QuizModel
import com.example.lebenindeutschland.screen.home.components.LinearProgress
import com.example.lebenindeutschland.screen.home.components.LinearProgress2
import com.example.lebenindeutschland.screen.viewModel.QuizViewModel
import com.example.lebenindeutschland.ui.theme.Green
import com.example.lebenindeutschland.ui.theme.Red
import com.example.lebenindeutschland.ui.theme.TextWhite
import com.example.lebenindeutschland.ui.theme.TextWhiteDarker
import com.example.lebenindeutschland.ui.theme.color1
import com.example.lebenindeutschland.ui.theme.color3


@SuppressLint("UnrememberedMutableState")
@Composable
fun QuizItem(
    viewModel: QuizViewModel = hiltViewModel(),
    quiz: QuizModel,
    totalQuiz: Int?,
    answerList: List<String>,
    quizIndex: Int,
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
//                contentScale = ContentScale
            )
        }



        Spacer(modifier = Modifier.height(10.dp))

        answerList.forEachIndexed { index, s ->
            AnswerItem(
                answer = s,
                icons = if (correctOrNot.value == true && index == answerIndex.value ||
                    correctOrNot.value == false && correctAnswerIndex == index
                ) Icons.Rounded.TaskAlt
                else if (correctOrNot.value == false && index == answerIndex.value) Icons.Outlined.Cancel
                else Icons.Outlined.Circle,
                color = if (correctOrNot.value == true && index == answerIndex.value ||
                    correctOrNot.value == false && correctAnswerIndex == index
                ) Green
                else if (correctOrNot.value == false && index == answerIndex.value) Red else TextWhite,
                borderColor = if (correctOrNot.value == true && index == answerIndex.value || correctOrNot.value == false && correctAnswerIndex == index) Green.copy(
                    alpha = .6f
                )
                else if (correctOrNot.value == false && index == answerIndex.value) Red.copy(alpha = .6f) else Color.Gray,
                isActiveState = isSelectedActive, fontSize = 20.sp
            ) {
                isSelectedActive = true
                updateAnswer.invoke(index)
                if (correctOrNot.value != null) {
                    viewModel.updateIsCoreorNot(quiz.id, correctOrNot.value == true)
                }
            }
        }
//        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 20.dp),
                enabled = quizIndex > 0,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(2.dp, color = color1),
                onClick = {
                    if (quizIndex > 0)
                        viewModel.quizIndex.value = viewModel.quizIndex.value - 1
                    isSelectedActive = false
                    correctOrNot.value = null
                }) {
                Text(
                    text = "Vorherige",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextWhite
                )

            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = color1),
                border = BorderStroke(2.dp, color = color3),
                onClick = {
                    if (isAnswered)
                        viewModel.quizIndex.value = viewModel.quizIndex.value + 1
                    isSelectedActive = false
                    correctOrNot.value = null
                }) {
                Text(
                    text = "Nächste",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextWhite
                )

            }


        }

        onClick()
    }

}


@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }

}


@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewQuizItem() {


}