package com.knotworking.inhabit.domain.usecase

import com.knotworking.inhabit.domain.repository.HabitRepository
import java.util.*

// Changing one usecase to use suspend rather than flow to compare the approaches
fun interface DeleteHabitUseCase : suspend (UUID) -> Result<Unit>

suspend fun deleteHabit(habitRepository: HabitRepository, habitId: UUID): Result<Unit> {
    // with suspend, Exceptions are not caught by this resultOf, but rather are picked up by the
    // error handler for the coroutine context
    return resultOf { habitRepository.deleteHabit(habitId) }
}