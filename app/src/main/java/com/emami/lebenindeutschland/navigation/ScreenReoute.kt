package com.emami.lebenindeutschland.navigation

sealed class ScreenReoute(val route: String) {
    object Home : ScreenReoute(route = "HOME")
    object AllQuiz : ScreenReoute(route = "ALLQUIZ")
    object FavQuiz : ScreenReoute(route = "FAVQUIZ")
    object MisQuiz : ScreenReoute(route = "MISQUIZ")
    object TestQuiz : ScreenReoute(route = "TESTQUIZ")
}
