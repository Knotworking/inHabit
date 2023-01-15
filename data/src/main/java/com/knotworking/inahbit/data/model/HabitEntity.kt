package com.knotworking.inahbit.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//TODO add entry relationship
@Entity(tableName = "habit_entity")
data class HabitEntity(
    @PrimaryKey val id: UUID,
    @ColumnInfo val name: String
)
