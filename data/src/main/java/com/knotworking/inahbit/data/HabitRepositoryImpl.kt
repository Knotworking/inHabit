package com.knotworking.inahbit.data

import com.knotworking.inahbit.data.db.HabitDao
import com.knotworking.inahbit.data.model.toDomain
import com.knotworking.inahbit.data.model.toEntity
import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class HabitRepositoryImpl(private val habitDao: HabitDao) : HabitRepository {
    override fun getHabits(): Flow<List<Habit>> = flow {
        //TODO look at example, there'll probably be a nicer way than first.
        // perhaps keeping the flow open to watch for changes
        emit(
            habitDao.getAll().first()
                .map {
                    it.toDomain()
                }
        )
    }

    override fun addHabit(habit: Habit): Flow<Unit> = flow {
        emit(habitDao.insertAll(habit.toEntity()))
    }
}