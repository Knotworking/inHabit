package com.knotworking.inhabit.detail

import com.knotworking.inhabit.model.HabitDisplayable

data class HabitDetailViewState(
    val hasError: Boolean = false,
    val loading: Boolean = false,
    val habit: HabitDisplayable? = null
)