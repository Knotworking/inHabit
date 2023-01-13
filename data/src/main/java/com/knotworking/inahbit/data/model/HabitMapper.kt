package com.knotworking.inahbit.data.model

import com.knotworking.inhabit.domain.model.Habit

fun Habit.toEntity() = HabitEntity(
    id = id,
    name = name
)