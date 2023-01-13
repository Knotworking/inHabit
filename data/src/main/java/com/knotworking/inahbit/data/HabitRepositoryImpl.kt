package com.knotworking.inahbit.data

import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class HabitRepositoryImpl : HabitRepository {
    override fun getHabits(): Flow<List<Habit>> = flow {
        emit(
            listOf(
                Habit(
                    id = UUID.randomUUID(),
                    name = "Habit 1",
                    entries = emptyList()
                )
            )
        )
    }
}