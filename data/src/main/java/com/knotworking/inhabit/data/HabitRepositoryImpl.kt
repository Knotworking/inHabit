package com.knotworking.inhabit.data

import android.util.Log
import com.knotworking.inhabit.data.db.HabitDao
import com.knotworking.inhabit.data.model.toDomain
import com.knotworking.inhabit.data.model.toEntity
import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.random.Random

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

    override fun getHabit(habitId: UUID): Flow<Habit> =
        habitDao.getById(id = habitId.toString())
            .map {
                // Adding in some random delay to test loading UI
                val randomDelay = Random.nextLong(500, 2000)
                delay(randomDelay)
                it
            }
            .map { map ->
                assert(map.keys.size == 1) {
                    "There should be exactly 1 habit matching a given id, however ${map.keys.size} were found."
                }

                map.keys.map { habitEntity ->
                    val habitEntries = map[habitEntity] ?: emptyList()
                    habitEntity.toDomain(habitEntries)
                }.first()
            }

    override fun addHabit(habit: Habit): Flow<Unit> = flow {
        Log.i("HabitRepositoryImpl", "addHabit")
        emit(habitDao.insertAll(habit.toEntity()))
    }

    override fun addHabitEntry(habitEntry: Habit.Entry): Flow<Unit> = flow {
        Log.i("HabitRepositoryImpl", "addHabitEntry")
        emit(habitDao.insertAll(habitEntry.toEntity()))
    }

    override suspend fun deleteHabit(habitId: UUID) {
        Log.i("HabitRepositoryImpl", "deleteHabit: $habitId")
        val result = habitDao.deleteById(id = habitId.toString())
        assert(result == 1) { "No habit found for ID" }
    }
}