package com.knotworking.inhabit.presentation.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.chargemap.compose.numberpicker.NumberPicker
import com.knotworking.inhabit.presentation.ui.theme.Typography

@Composable
fun AddHabitEntryDialog(
    showDialog: MutableState<Boolean>,
    viewModel: AddHabitEntryViewModel = hiltViewModel(),
) {
    val viewState by viewModel.addHabitEntryViewStateFlow.collectAsState()

    Dialog(onDismissRequest = { showDialog.value = false }) {
        Card(
            shape = RoundedCornerShape(size = 8.dp),
            modifier = Modifier.padding(8.dp),
        ) {
            AddHabitEntryDialogContent(showDialog = showDialog,
                unitLabel = viewState.habit?.unitLabel ?: "",
                onAddClick = { unitCount ->
                    viewModel.addEntry(unitCount = unitCount)
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitEntryDialogContent(
    modifier: Modifier = Modifier,
    unitLabel: String,
    showDialog: MutableState<Boolean>,
    onAddClick: (unitCount: Int) -> Unit
) {
    val unitCountField = remember { mutableStateOf(0) }

    Column(modifier = modifier.background(color = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Record new activity",
                style = Typography.titleMedium
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                NumberPicker(
                    value = unitCountField.value,
                    onValueChange = { unitCountField.value = it },
                    range = 0..999,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                )
                if (unitLabel.isEmpty()) {
                    CircularProgressIndicator()
                } else {
                    Text(text = unitLabel)
                }

            }
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
                onAddClick(unitCountField.value)
                showDialog.value = false
            }) {
                Text(text = "Done")
            }
        }
    }

}