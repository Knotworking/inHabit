package com.knotworking.inhabit.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object Home : Destination {
    override val route = "home"
}

object HabitDetail : Destination {
    override val route = "habit_detail"
    const val habitIdArg = "habit_id"
    val routeWithArgs = "$route/{$habitIdArg}"
    val arguments = listOf(
        navArgument(habitIdArg) { type = NavType.StringType }
    )
}