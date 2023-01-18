package com.knotworking.inhabit.detail.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.knotworking.inhabit.common.LoadingContent
import com.knotworking.inhabit.detail.HabitDetailViewModel

@Composable
fun HabitDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: HabitDetailViewModel = hiltViewModel()
) {
    val viewState by viewModel.habitDetailViewStateFlow.collectAsState()

    if (viewState.habit == null || viewState.loading) {
        LoadingContent(modifier)
    } else {
        Column(modifier = modifier) {
            Text(text = viewState.habit!!.name)
            Text(text = viewState.habit!!.entries.size.toString())
        }
    }


}