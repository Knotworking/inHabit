package com.knotworking.inahbit.data

import android.util.Log
import com.knotworking.inahbit.data.db.HabitDao
import com.knotworking.inahbit.data.model.toDomain
import com.knotworking.inahbit.data.model.toEntity
import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.*

class HabitRepositoryImpl(private val habitDao: HabitDao) : HabitRepository {
    override fun getHabits(): Flow<List<Habit>> =
        habitDao.getAll().map { map ->
            map.keys.map { habitEntity ->
                val habitEntries = map[habitEntity] ?: emptyList()
                habitEntity.toDomain(habitEntries)
            }
        }.onEach {
            Log.i("HabitRepositoryImpl", "getHabits onEach: ${it.size}")
        }

    override fun addHabit(habit: Habit): Flow<Unit> = flow {
        Log.i("HabitRepositoryImpl", "addHabit")
        emit(habitDao.insertAll(habit.toEntity()))
    }

    override fun addHabitEntry(habitEntry: Habit.Entry): Flow<Unit> = flow {
        Log.i("HabitRepositoryImpl", "addHabitEntry")
        emit(habitDao.insertAll(habitEntry.toEntity()))
    }
}