package com.knotworking.inhabit.domain.usecase

import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

// Advantage of wrapping in result object:
// It doesn't break the stream, reduces amount of null handling
fun interface GetHabitsUseCase : () -> Flow<Result<List<Habit>>>

fun getHabits(habitRepository: HabitRepository): Flow<Result<List<Habit>>> = habitRepository
    .getHabits().map {
        resultOf { it }
    }.catch {
        // for other than IOException but it will stop collecting Flow
        emit(Result.failure(it)) // also catch does re-throw CancellationException
    }