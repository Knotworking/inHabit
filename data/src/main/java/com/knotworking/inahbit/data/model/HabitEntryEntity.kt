package com.knotworking.inahbit.data.model

import java.util.*

data class HabitEntryEntity(
    val id: UUID,
    val habitId: UUID,
    val timestamp: Long
)
