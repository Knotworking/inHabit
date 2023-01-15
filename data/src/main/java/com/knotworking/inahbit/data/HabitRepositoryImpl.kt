package com.knotworking.inahbit.data

import com.knotworking.inahbit.data.db.HabitDao
import com.knotworking.inahbit.data.model.toDomain
import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.util.*

class HabitRepositoryImpl(private val habitDao: HabitDao) : HabitRepository {
    override fun getHabits(): Flow<List<Habit>> = flow {
        emit(
            listOf(
                Habit(
                    id = UUID.randomUUID(),
                    name = "Habit 1",
                    entries = emptyList()
                )
            ).plus(
                // just a temporary way whilst connecting dummy lists
                habitDao.getAll().first()
                    .map {
                        it.toDomain()
                    }
            )
        )
    }
}