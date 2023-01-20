package com.knotworking.inhabit.presentation.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knotworking.inhabit.presentation.add.AddHabitDialog
import com.knotworking.inhabit.presentation.home.HomeViewModel
import com.knotworking.inhabit.presentation.common.model.HabitDisplayable
import java.util.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    setFabOnClick: ((() -> Unit)?) -> Unit,
    onHabitClick: (habitId: UUID) -> Unit
) {
    val homeViewState by viewModel.homeViewStateFlow.collectAsState()
    val showAddHabitDialog = remember { mutableStateOf(false) }

    // https://stackoverflow.com/questions/71186641/how-to-control-a-scaffolds-floatingactionbutton-onclick-from-child-composable
    LaunchedEffect(Unit) {
        setFabOnClick { showAddHabitDialog.value = true }
    }
    
    if (showAddHabitDialog.value) {
        AddHabitDialog(showDialog = showAddHabitDialog,
            onAddHabit = { name, unitLabel ->
            viewModel.addHabit(name = name, unitLabel = unitLabel)
        })
    }

    HomeScreenContent(
        modifier = modifier,
        habits = homeViewState.habits,
        onDeleteClick = { habitId -> viewModel.deleteHabit(habitId) },
        onHabitClick = onHabitClick
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    habits: List<HabitDisplayable>,
    onDeleteClick: (habitId: UUID) -> Unit,
    onHabitClick: (habitId: UUID) -> Unit
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
                onDeleteClick = onDeleteClick,
                onHabitClick = onHabitClick
            )
        }
    }
}