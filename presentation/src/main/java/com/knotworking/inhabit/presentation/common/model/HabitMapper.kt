package com.knotworking.inhabit.presentation.common.model

import com.knotworking.inhabit.domain.model.Habit
import java.util.*

fun Habit.toDisplayable() = HabitDisplayable(
    id = id,
    name = name,
    unitLabel = unitLabel,
    entries = entries.map {
        HabitDisplayable.Entry(
            id = it.id,
            habitId = it.habitId,
            timestamp = it.timestamp,
            unitCount = it.unitCount
        )
    }
)

fun createHabitEntry(habitId: UUID, unitCount: Int): Habit.Entry = Habit.Entry(
    id = UUID.randomUUID(),
    habitId = habitId,
    timestamp = System.currentTimeMillis(),
    unitCount = unitCount
)