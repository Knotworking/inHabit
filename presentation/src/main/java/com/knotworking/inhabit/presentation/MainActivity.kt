package com.knotworking.inhabit.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.knotworking.inhabit.presentation.navigation.HabitDetail
import com.knotworking.inhabit.presentation.navigation.Home
import com.knotworking.inhabit.presentation.navigation.InHabitNavHost
import com.knotworking.inhabit.presentation.ui.theme.InHabitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InHabitApp()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InHabitApp() {
    InHabitTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination

        val fabSettings = getFabSettingsForDestination(currentDestination)
        val (fabOnClick, setFabOnClick) = remember { mutableStateOf<(() -> Unit)?>(null) }

        Scaffold(
            floatingActionButton =
            {
                AnimatedVisibility(
                    visible = fabSettings.showFab,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    FloatingActionButton(
                        onClick = {
                            fabOnClick?.invoke()
                        }, shape = RoundedCornerShape(percent = 50),
                        containerColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Icon(
                            imageVector = fabSettings.icon,
                            contentDescription = stringResource(fabSettings.contentDescription),
                            tint = Color.White
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End,
        ) { padding ->
            InHabitNavHost(
                navController = navController,
                modifier = Modifier.padding(padding),
                setFabOnClick = setFabOnClick
            )
        }
    }
}

private fun getFabSettingsForDestination(destination: NavDestination?): FabSettings {
    return when (destination?.route?.substringBefore("/")) {
        Home.route -> FabSettings(showFab = true, contentDescription = R.string.add_habit_action)
        HabitDetail.route -> FabSettings(showFab = true, contentDescription = R.string.add_habit_entry_action)
        else -> FabSettings(showFab = false)
    }
}

private data class FabSettings(
    val showFab: Boolean,
    val icon: ImageVector = Icons.Filled.Add,
    val contentDescription: Int = R.string.unknown_action
)