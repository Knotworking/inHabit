package com.knotworking.inhabit.presentation.common.model

import java.util.*

data class HabitDisplayable(
    val id: UUID,
    val name: String,
    val entries: List<Entry>
) {
    data class Entry(
        val id: UUID,
        val habitId: UUID,
        val timestamp: Long
    )
}
