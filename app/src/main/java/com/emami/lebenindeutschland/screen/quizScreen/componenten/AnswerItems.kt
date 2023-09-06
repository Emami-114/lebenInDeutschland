package com.emami.lebenindeutschland.screen.quizScreen.componenten

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emami.lebenindeutschland.ui.theme.TextWhite
import com.emami.lebenindeutschland.ui.theme.color3

@Composable
fun AnswerItem(
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
                .size(28.dp).weight(1f)
                .clickable { if (!isActiveState) onClick.invoke() }
        )

        }

    }

}

@Preview
@Composable
fun PreviewAnswerItem() {
    AnswerItem("Frage1", Icons.Rounded.Close, TextWhite, Color.Gray, isActiveState = false) {}
}