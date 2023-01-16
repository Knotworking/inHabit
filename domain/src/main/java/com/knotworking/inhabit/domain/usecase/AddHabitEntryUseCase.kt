package com.knotworking.inhabit.domain.usecase

import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun interface AddHabitEntryUseCase : (Habit.Entry) -> Flow<Result<Unit>>

fun addHabitEntry(habitRepository: HabitRepository, habitEntry: Habit.Entry): Flow<Result<Unit>> = habitRepository
    .addHabitEntry(habitEntry)
    .map {
        Result.success(it)
    }
    .catch {
        emit(Result.failure(it))
    }