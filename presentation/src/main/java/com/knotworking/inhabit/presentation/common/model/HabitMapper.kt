package com.knotworking.inhabit.presentation.common.model

import com.knotworking.inhabit.domain.model.Habit
import java.util.*

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

fun createHabitEntry(habitId: UUID): Habit.Entry = Habit.Entry(
    id = UUID.randomUUID(),
    habitId = habitId,
    timestamp = System.currentTimeMillis()
)