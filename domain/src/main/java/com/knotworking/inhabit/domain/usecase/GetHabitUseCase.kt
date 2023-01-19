package com.knotworking.inhabit.domain.usecase

import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.UUID

fun interface GetHabitUseCase : (UUID) -> Flow<Result<Habit>>

fun getHabit(habitRepository: HabitRepository, habitId: UUID): Flow<Result<Habit>> =
    habitRepository.getHabit(habitId = habitId)
        .map {
            resultOf { it }
        }.catch {
            emit(Result.failure(it))
        }