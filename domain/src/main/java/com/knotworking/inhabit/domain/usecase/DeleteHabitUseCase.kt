package com.knotworking.inhabit.domain.usecase

import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.*

fun interface DeleteHabitUseCase : (UUID) -> Flow<Result<Unit>>

fun deleteHabit(habitRepository: HabitRepository, habitId: UUID): Flow<Result<Unit>> = habitRepository
    .deleteHabit(habitId = habitId)
    .map {
        Result.success(it)
    }
    .catch {
        emit(Result.failure(it))
    }