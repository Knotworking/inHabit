package com.knotworking.inhabit.home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knotworking.inhabit.home.HomeViewModel
import com.knotworking.inhabit.ui.theme.InHabitTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val homeViewState by viewModel.homeViewStateFlow.collectAsState()
    //TODO move scaffold up to main activity
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addHabit() }) {
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
        Column(modifier = Modifier.padding(padding)) {
            Greeting("${homeViewState.habits.map {
                    habit -> "${habit.name} ${habit.entries.map { it.timestamp }}" 
            }}")

            if (homeViewState.habits.isNotEmpty()) {
                Button(onClick = {
                    val latestHabitId = homeViewState.habits.last().id
                    viewModel.deleteHabit(latestHabitId)
                }) {
                    Text(text = "Delete")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InHabitTheme {
        Greeting("Android")
    }
}