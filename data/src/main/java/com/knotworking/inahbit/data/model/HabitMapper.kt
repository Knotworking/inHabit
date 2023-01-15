package com.knotworking.inahbit.data.model

import com.knotworking.inhabit.domain.model.Habit

fun Habit.toEntity() = HabitEntity(
    id = id,
    name = name
)

//TODO implement relationship
fun HabitEntity.toDomain() = Habit(
    id = id,
    name = name,
    entries = emptyList()
)