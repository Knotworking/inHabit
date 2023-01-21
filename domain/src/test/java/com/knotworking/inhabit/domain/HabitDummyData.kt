package com.knotworking.inhabit.domain

import com.knotworking.inhabit.domain.model.Habit
import java.util.*

private val habit1Id = UUID.randomUUID()

internal val habit1Entry1 = Habit.Entry(
    id = UUID.randomUUID(),
    habitId = habit1Id,
    timestamp = 1674231702,
    unitCount = 5
)

internal val habit1 = Habit(
    id = habit1Id,
    name = "habit 1",
    unitLabel = "minutes",
    entries = listOf(habit1Entry1)
)



internal fun generateTestHabits(): List<Habit> = listOf(habit1)