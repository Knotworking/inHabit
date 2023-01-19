package com.knotworking.inhabit.domain.repository

import com.knotworking.inhabit.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HabitRepository {
    fun getHabits(): Flow<List<Habit>>
    fun getHabit(habitId: UUID): Flow<Habit>
    fun addHabit(habit: Habit): Flow<Unit>
    fun addHabitEntry(habitEntry: Habit.Entry): Flow<Unit>
    fun deleteHabit(habitId: UUID): Flow<Unit>
}