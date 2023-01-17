package com.knotworking.inhabit.home.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knotworking.inhabit.home.HomeViewModel
import com.knotworking.inhabit.model.HabitDisplayable
import java.util.*

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
        HomeScreenContent(
            modifier = Modifier.padding(padding),
            habits = homeViewState.habits,
            deleteHabit = { habitId -> viewModel.deleteHabit(habitId) }
        )
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    habits: List<HabitDisplayable>,
    deleteHabit: (habitId: UUID) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 180.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(habits, key = { it.id }) { habit ->
            HabitGridItem(
                habit = habit,
                deleteHabit = deleteHabit
            )
        }
    }
}