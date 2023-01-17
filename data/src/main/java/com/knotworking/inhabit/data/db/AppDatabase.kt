package com.knotworking.inhabit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.knotworking.inhabit.data.model.HabitEntity
import com.knotworking.inhabit.data.model.HabitEntryEntity

@Database(entities = [HabitEntity::class, HabitEntryEntity::class], version = 1)
@TypeConverters(UUIDConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}