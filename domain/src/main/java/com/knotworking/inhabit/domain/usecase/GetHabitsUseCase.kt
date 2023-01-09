package com.knotworking.inhabit.domain.usecase

import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

//TODO read pros and cons of wrapping in a Result object
fun interface GetHabitsUseCase : () -> Flow<Result<List<String>>>

fun getHabits(habitRepository: HabitRepository): Flow<Result<List<String>>> = habitRepository
    .getHabits().map {
        resultOf { it }
    }.catch { // for other than IOException but it will stop collecting Flow
        emit(Result.failure(it)) // also catch does re-throw CancellationException
    }