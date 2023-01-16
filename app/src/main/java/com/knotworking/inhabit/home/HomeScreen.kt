package com.knotworking.inhabit.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knotworking.inhabit.ui.theme.InHabitTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val habitViewState by viewModel.habitsViewState.collectAsState()
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
                // Defaults to null, that is, No cutout
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50)
                )
            ) {

            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Greeting("${habitViewState.habits.map { habit -> "${habit.name} ${habit.entries.map { it.timestamp }}" }}")
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