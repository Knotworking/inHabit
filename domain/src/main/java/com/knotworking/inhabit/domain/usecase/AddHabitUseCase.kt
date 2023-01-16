package com.knotworking.inhabit.domain.usecase

import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

// Cannot name parameters with this approach
fun interface AddHabitUseCase : (Habit) -> Flow<Result<Unit>>

fun addHabit(habitRepository: HabitRepository, habit: Habit): Flow<Result<Unit>> = habitRepository
    .addHabit(habit)
    .map {
        //TODO check that we don't come here in case of error
        Result.success(it)
    }
    .catch {
        emit(Result.failure(it))
    }