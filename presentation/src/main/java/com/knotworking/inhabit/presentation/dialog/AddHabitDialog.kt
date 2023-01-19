package com.knotworking.inhabit.presentation.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddHabitDialog(showDialog: MutableState<Boolean>) {
    Dialog(onDismissRequest = { showDialog.value = false }) {

    }
}

@Composable
fun AddHabitDialogContent(modifier: Modifier = Modifier, showDialog: MutableState<Boolean>){
    Card(shape = RoundedCornerShape(size = 8.dp),
    modifier = Modifier.padding(8.dp),
    ) {
        Column(modifier = modifier) {
            Text(text = "")
        }
    }
}

