package com.knotworking.inhabit.home

import com.knotworking.inhabit.model.HabitDisplayable

//TODO look at example project for handling errors
data class HomeViewState(
    val hasError: Boolean = false,
    val loading: Boolean = false,
    val habits: List<HabitDisplayable> = listOf()
)
