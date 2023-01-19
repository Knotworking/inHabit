package com.knotworking.inhabit.presentation.add

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.knotworking.inhabit.presentation.ui.theme.Typography

@Composable
fun AddHabitDialog(
    showDialog: MutableState<Boolean>,
    onAddHabit: (habitName: String) -> Unit
) {
    Dialog(onDismissRequest = { showDialog.value = false }) {
        AddHabitDialogContent(showDialog = showDialog, onAddHabit = onAddHabit)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitDialogContent(
    modifier: Modifier = Modifier,
    showDialog: MutableState<Boolean>,
    onAddHabit: (habitName: String) -> Unit
) {
    val habitNameField = remember { mutableStateOf("") }

    Card(
        shape = RoundedCornerShape(size = 8.dp),
        modifier = Modifier.padding(8.dp),
    ) {
        Column(modifier = modifier.background(color = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Give your new habit a name.",
                    style = Typography.titleMedium
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = habitNameField.value,
                    onValueChange = { habitNameField.value = it },
                    placeholder = { Text(text = "Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = { showDialog.value = false }) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = {
                    onAddHabit(habitNameField.value)
                    showDialog.value = false
                }) {
                    Text(text = "Done")
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(name = "Add Habit Dialog")
@Composable
fun AddHabitDialogContentPreview() {
    AddHabitDialogContent(
        showDialog = mutableStateOf(false),
        onAddHabit = {}
    )
}