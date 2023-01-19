package com.knotworking.inhabit.presentation.home

import com.knotworking.inhabit.presentation.common.model.HabitDisplayable

//TODO look at example project for handling errors
data class HomeViewState(
    val hasError: Boolean = false,
    val loading: Boolean = false,
    val habits: List<HabitDisplayable> = listOf()
)
