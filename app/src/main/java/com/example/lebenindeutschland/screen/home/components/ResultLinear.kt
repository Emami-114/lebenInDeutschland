package com.example.lebenindeutschland.screen.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.lebenindeutschland.ui.theme.TextWhite
import com.example.lebenindeutschland.ui.theme.TextWhiteDarker
import com.example.lebenindeutschland.ui.theme.color1
import com.example.lebenindeutschland.ui.theme.color3

@Composable
fun LinearProgress(
    score: Int,
    total: Int,
    gradientColors: List<Color> = listOf(
        Color(0xFF6ce0c4),
        Color(0xFF40c7e7),
        Color(0xFF6ce0c4),
        Color(0xFF40c7e7)
    ),
) {
    val score2 = if (score > total) {
        total
    } else score

    var canvasSize by remember { mutableStateOf(Size.Zero) }

//    val animatedScore = animateFloatAsState(
//        targetValue = score2, animationSpec = tween(
//            durationMillis = 1000,
//        )

    var animatedScore by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = score2) {
        animatedScore = score2
    }

    val reciveValue by animateIntAsState(targetValue = score2, animationSpec = tween(2000))

    val progress2 = total.toFloat().let { animatedScore.toFloat() / it } * 100
    val progress = total.toFloat().let { animatedScore.toFloat() / it }.times(canvasSize.width)


    val sweepAngele by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1500)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = Modifier
                .padding(start = 15.dp, top = 18.dp)
                .weight(9f)
                .height(15.dp)
                .onGloballyPositioned { coordinate ->
                    canvasSize = coordinate.size.toSize()
                }
                .align(Alignment.CenterVertically)
        ) {
            drawLine(
                color = Color.Gray.copy(alpha = .6f),
                cap = StrokeCap.Round,
                strokeWidth = size.height,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = size.width, y = 0f)
            )
//            val progress = total.toFloat().let { animatedScore.toFloat() / it }.times(size.width)


            sweepAngele.let { Offset(x = it, y = 0f) }?.let {
                drawLine(
                    brush = Brush.linearGradient(colors = gradientColors),
                    cap = StrokeCap.Round,
                    strokeWidth = size.height,
                    start = Offset(x = 0f, y = 0f),
                    end = it
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(2.5f)
//                .padding(bottom = 30.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {


            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = TextWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                ) {
                    append("${reciveValue}")
//                    AnimatedCounter(
//                        count = score2.toInt() + 1,
//                        bigTextColor = TextWhite,
//                        bigTextFontSize = 30.sp
//                    )


                }
            })
        }

    }
}


@Composable
fun LinearProgress2(
    score: Int,
    total: Int?,
    gradientColors: List<Color> = listOf(
        Color(0xFF6ce0c4),
        Color(0xFF40c7e7),
        Color(0xFF6ce0c4),
        Color(0xFF40c7e7)
    ),
) {
    val score2 = if (score > (total ?: 0)) {
        total
    } else score

    var canvasSize by remember { mutableStateOf(Size.Zero) }


    var animatedScore by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = score2) {
        animatedScore = score2 ?: 0
    }

    val reciveValue by animateIntAsState(targetValue = score2 ?: 0)

//    val progress2 = total.toFloat().let { animatedScore.toFloat() / it } * 100
    val progress = (animatedScore.toFloat() / total?.toFloat()!!).times(canvasSize.width)


    val sweepAngele by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1500)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Canvas(
            modifier = Modifier
                .padding(start = 15.dp, top = 18.dp)
                .weight(9f)
                .height(15.dp)
                .onGloballyPositioned { coordinate ->
                    canvasSize = coordinate.size.toSize()
                }
                .align(Alignment.CenterVertically)
        ) {
            drawLine(
                color = Color.Gray.copy(alpha = .6f),
                cap = StrokeCap.Round,
                strokeWidth = size.height,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = size.width, y = 0f)
            )
//            val progress = total.toFloat().let { animatedScore.toFloat() / it }.times(size.width)


            sweepAngele.let { Offset(x = it, y = 0f) }?.let {
                drawLine(
                    brush = Brush.linearGradient(colors = gradientColors),
                    cap = StrokeCap.Round,
                    strokeWidth = size.height,
                    start = Offset(x = 0f, y = 0f),
                    end = it
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(3f).padding(end = 10.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            AnimatedCounter(
                count = reciveValue+1,
                bigTextColor = TextWhite,
                bigTextFontSize = 22.sp
            )

            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = TextWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    )
                ) {
                    append("/")
                    withStyle(
                        style = SpanStyle(
                            color = TextWhiteDarker,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    ){
                        append("${total+1}")
                    }
                }
            })
        }

    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCounter(
    count: Int = 0,
    bigTextColor: Color,
    bigTextFontSize: TextUnit,

    ) {
    var oldCounter by remember { mutableStateOf(count) }

    SideEffect {
        oldCounter = count
    }

    Row() {
        val counterString = count.toString()
        val oldCounterString = oldCounter.toString()

        for (i in counterString.indices) {
            val oldChar = oldCounterString.getOrNull(i)
            val newChar = counterString[i]
            val char = if (oldChar == newChar) {
                oldCounterString[i]
            } else {
                counterString[i]
            }
            AnimatedContent(targetState = char, transitionSpec = {
                slideInVertically { it } with slideOutVertically { -it }
            }) { char ->
                Text(
                    text = "$char",
                    fontWeight = FontWeight.Bold,
                    color = bigTextColor,
                    fontSize = bigTextFontSize
                )
            }
        }

    }

}


@Preview
@Composable
fun PreviewLinearProgress() {
//    LinearProgress(score = 50, total = 100)
    LinearProgress2(score = 100, total = 100)
}