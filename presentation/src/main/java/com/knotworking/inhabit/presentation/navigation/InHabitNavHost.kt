package com.knotworking.inhabit.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knotworking.inhabit.presentation.detail.composable.HabitDetailScreen
import com.knotworking.inhabit.presentation.home.composable.HomeScreen
import java.util.*

@Composable
fun InHabitNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    setFabOnClick: ((() -> Unit)?) -> Unit = {}
) {
    NavHost(navController = navController,
    startDestination = Home.route,
    modifier = modifier) {
        composable(route = Home.route) {
            HomeScreen(setFabOnClick = setFabOnClick,
                onHabitClick = { habitId ->
                    navController.navigateToHabitDetail(habitId = habitId)
                })
        }
        composable(
            route = HabitDetail.routeWithArgs,
            arguments = HabitDetail.arguments
        ) {
            HabitDetailScreen(setFabOnClick = setFabOnClick)
        }
    }
}

private fun NavHostController.navigateToHabitDetail(habitId: UUID) {
    this.navigate("${HabitDetail.route}/$habitId")
}