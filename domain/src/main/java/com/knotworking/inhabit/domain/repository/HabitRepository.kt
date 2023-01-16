package com.knotworking.inhabit.domain.repository

import com.knotworking.inhabit.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getHabits() : Flow<List<Habit>>
    fun addHabit(habit: Habit) : Flow<Unit>
}