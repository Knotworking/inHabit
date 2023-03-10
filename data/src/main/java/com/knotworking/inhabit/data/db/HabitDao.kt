package com.knotworking.inhabit.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.knotworking.inhabit.data.model.HabitEntity
import com.knotworking.inhabit.data.model.HabitEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query(
        "SELECT * FROM habit" +
                " LEFT JOIN habit_entry ON habit.id = habit_entry.habit_id"
    )
    fun getAll(): Flow<Map<HabitEntity, List<HabitEntryEntity>>>

    @Query(
        "SELECT * FROM habit" +
                " LEFT JOIN habit_entry ON habit.id = habit_entry.habit_id" +
                " WHERE habit.id = :id"
    )
    fun getById(id: String): Flow<Map<HabitEntity, List<HabitEntryEntity>>>

    @Insert
    suspend fun insertAll(vararg habits: HabitEntity)

    @Insert
    suspend fun insertAll(vararg habitEntries: HabitEntryEntity)

    @Query("DELETE FROM habit WHERE id = :id")
    suspend fun deleteById(id: String): Int
}