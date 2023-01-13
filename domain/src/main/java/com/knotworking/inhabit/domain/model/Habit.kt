package com.knotworking.inhabit.domain.model

import java.util.UUID

data class Habit(
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


