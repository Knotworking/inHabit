package com.knotworking.inhabit.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

//TODO read pros and cons of wrapping in a Result object
fun interface GetHabitsUseCase : () -> Flow<Result<List<String>>>

fun getHabits(): Flow<Result<List<String>>> = flow {
    emit(Result.success(listOf("habit 1", "habit 2")))
}.catch { // for other than IOException but it will stop collecting Flow
    emit(Result.failure(it)) // also catch does re-throw CancellationException
}