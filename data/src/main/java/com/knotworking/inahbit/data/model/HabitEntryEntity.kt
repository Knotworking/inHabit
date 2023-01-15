package com.knotworking.inahbit.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "habit_entry_entity")
data class HabitEntryEntity(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "habit_id") val habitId: UUID,
    @ColumnInfo val timestamp: Long
)
