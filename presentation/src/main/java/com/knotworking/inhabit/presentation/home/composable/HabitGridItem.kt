package com.knotworking.inhabit.presentation.home.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.knotworking.inhabit.presentation.common.model.HabitDisplayable
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitGridItem(
    modifier: Modifier = Modifier,
    habit: HabitDisplayable,
    onDeleteClick: (habitId: UUID) -> Unit,
    onHabitClick: (habitId: UUID) -> Unit
) {
    Card(
        onClick = { onHabitClick(habit.id) },
        modifier = modifier.padding(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(text = habit.name)
            Text(text = "${habit.entries.size} entries")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { onDeleteClick(habit.id) }) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Extra actions for habit"
                    )
                }
            }
        }
    }
}