package com.knotworking.inahbit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.knotworking.inahbit.data.model.HabitEntity
import com.knotworking.inahbit.data.model.HabitEntryEntity

@Database(entities = [HabitEntity::class, HabitEntryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}