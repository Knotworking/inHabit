package com.knotworking.inhabit.presentation.detail.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knotworking.inhabit.presentation.add.AddHabitEntryDialog
import com.knotworking.inhabit.presentation.common.composable.LoadingContent
import com.knotworking.inhabit.presentation.common.composable.GenericErrorContent
import com.knotworking.inhabit.presentation.common.model.HabitDisplayable
import com.knotworking.inhabit.presentation.detail.HabitDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HabitDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: HabitDetailViewModel = hiltViewModel(),
    setFabOnClick: ((() -> Unit)?) -> Unit,
) {
    val viewState by viewModel.habitDetailViewStateFlow.collectAsState()
    val showAddHabitEntryDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        setFabOnClick {
            viewState.habit?.let {
                showAddHabitEntryDialog.value = true
            }
        }
    }

    if (showAddHabitEntryDialog.value) {
        AddHabitEntryDialog(showDialog = showAddHabitEntryDialog)
    }

    if (viewState.hasError) {
        GenericErrorContent()
    } else if (viewState.habit == null || viewState.loading) {
        LoadingContent(modifier)
    } else {
        HabitDetailContent(
            modifier = modifier,
            habit = viewState.habit!!
        )
    }
}

@Composable
fun HabitDetailContent(
    modifier: Modifier = Modifier,
    habit: HabitDisplayable
) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = habit.name,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Text(text = "${habit.entries.size} entries")
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(habit.entries) { entry ->
                HabitEntryRow(entry = entry, unitLabel = habit.unitLabel)
            }
        }
    }
}

@Composable
fun HabitEntryRow(entry: HabitDisplayable.Entry, unitLabel: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = entry.id.toString().take(3))
            Text(text = "${entry.unitCount} $unitLabel")
            Text(text = entry.timestamp.toDateTime())
        }
    }
}

//TODO needs refactoring, should be somewhere else, probably don't have to do this for each row
fun Long.toDateTime(): String {
    val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
    val netDate = Date(this)
    return sdf.format(netDate)
}