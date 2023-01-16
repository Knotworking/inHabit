package com.knotworking.inahbit.data.model

import com.knotworking.inhabit.domain.model.Habit

fun Habit.toEntity() = HabitEntity(
    id = id,
    name = name
)

fun HabitEntity.toDomain(entries: List<HabitEntryEntity>) = Habit(
    id = id,
    name = name,
    entries = entries.map { it.toDomain() }
)

fun Habit.Entry.toEntity() = HabitEntryEntity(
    id = id,
    habitId = habitId,
    timestamp = timestamp
)

fun HabitEntryEntity.toDomain() = Habit.Entry(
    id = id,
    habitId = habitId,
    timestamp = timestamp
)