package com.knotworking.inhabit.model

import com.knotworking.inhabit.domain.model.Habit

fun Habit.toDisplayable() = HabitDisplayable(
    id = id,
    name = name,
    entries = entries.map {
        HabitDisplayable.Entry(
            id = it.id,
            habitId = it.habitId,
            timestamp = it.timestamp
        )
    }
)