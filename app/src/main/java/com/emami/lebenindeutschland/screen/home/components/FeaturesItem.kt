package com.emami.lebenindeutschland.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Quiz
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.emami.lebenindeutschland.navigation.ScreenReoute
import com.emami.lebenindeutschland.screen.quizScreen.componenten.noRippleClickable
import com.emami.lebenindeutschland.ui.theme.TextWhite
import com.emami.lebenindeutschland.ui.theme.color3


@Composable
fun Features(navController: NavController) {

    val featuresList = listOf(
        FeaturesModel(
            "Übung",
            Icons.Rounded.School,
            onClick = { navController.navigate(ScreenReoute.AllQuiz.route) }),
        FeaturesModel("Letzter Fehler", Icons.Rounded.Quiz, onClick = {
            navController.navigate(ScreenReoute.MisQuiz.route)
        }),
        FeaturesModel("Prüfen", Icons.Rounded.WorkspacePremium, onClick = {
            navController.navigate(ScreenReoute.TestQuiz.route)
        }),
        FeaturesModel("Lesezeichen", Icons.Rounded.Bookmarks, onClick = {
            navController.navigate(ScreenReoute.FavQuiz.route)
        }),
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(featuresList) {
                FeatureItem(icon = it.icon, title = it.title) {
                    it.onClick.invoke()
                }
            }
        }
    }

}


@Composable
fun FeatureItem(icon: ImageVector, title: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color3)
            .height(170.dp)
            .noRippleClickable { onClick.invoke() }
            .padding(20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TextWhite,
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = title, color = TextWhite,
                fontSize = 18.sp, fontWeight = FontWeight.Medium,
            )

        }
    }


}

@Preview
@Composable
fun PreviewFeaturesItem() {
//    FeatureItem(icon = Icons.Default.Email, title = "Übung") {}
    Features(rememberNavController())
}


data class FeaturesModel(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
)