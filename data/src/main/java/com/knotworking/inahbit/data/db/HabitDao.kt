package com.knotworking.inahbit.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.knotworking.inahbit.data.model.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit_entity")
    fun getAll(): Flow<List<HabitEntity>>

    @Insert
    fun insertAll(vararg habits: HabitEntity)

    //TODO
    //fun delete(id: UUID)
}