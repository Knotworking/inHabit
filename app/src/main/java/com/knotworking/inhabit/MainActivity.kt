package com.knotworking.inhabit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.knotworking.inhabit.navigation.InHabitNavHost
import com.knotworking.inhabit.ui.theme.InHabitTheme
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

@Composable
fun InHabitApp() {
    InHabitTheme {
        val navController = rememberNavController()
        val (fabOnClick, setFabOnClick) = remember { mutableStateOf<(() -> Unit)?>(null) }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    fabOnClick?.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add habit",
                        tint = Color.White
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomAppBar(
                    // Defaults to null/no cutout
                    cutoutShape = MaterialTheme.shapes.small.copy(
                        CornerSize(percent = 50)
                    )
                ) {

                }
            }
        ) { padding ->
            InHabitNavHost(
                navController = navController,
                modifier = Modifier.padding(padding),
                setFabOnClick = setFabOnClick
            )
        }
    }
}