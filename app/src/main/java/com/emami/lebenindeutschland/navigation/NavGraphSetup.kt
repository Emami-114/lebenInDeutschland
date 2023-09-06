package com.emami.lebenindeutschland.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emami.lebenindeutschland.screen.home.HomeScreen
import com.emami.lebenindeutschland.screen.quizScreen.AllQuizScreen
import com.emami.lebenindeutschland.screen.quizScreen.FavoriteQuizScreen
import com.emami.lebenindeutschland.screen.quizScreen.MistakeQuizScreen
import com.emami.lebenindeutschland.screen.quizScreen.TestQuizScreen

@Composable
fun NavGraphSetup(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ScreenReoute.Home.route) {
        composable(route = ScreenReoute.Home.route) { HomeScreen(navController = navController) }
        composable(route = ScreenReoute.AllQuiz.route) { AllQuizScreen(navController = navController) }
        composable(route = ScreenReoute.FavQuiz.route) { FavoriteQuizScreen(navController = navController) }
        composable(route = ScreenReoute.MisQuiz.route) { MistakeQuizScreen(navController = navController) }
        composable(route = ScreenReoute.TestQuiz.route) { TestQuizScreen(navController = navController) }
    }

}