package com.knotworking.inhabit.presentation.detail

import com.knotworking.inhabit.presentation.model.HabitDisplayable

data class HabitDetailViewState(
    val hasError: Boolean = false,
    val loading: Boolean = false,
    val habit: HabitDisplayable? = null
)