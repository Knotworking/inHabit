package com.knotworking.inhabit.presentation.add

import com.knotworking.inhabit.presentation.common.model.HabitDisplayable

data class AddHabitEntryViewState(
    val hasError: Boolean = false,
    val loading: Boolean = false,
    val habit: HabitDisplayable? = null
)
