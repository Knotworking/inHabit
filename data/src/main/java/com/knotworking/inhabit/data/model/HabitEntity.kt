package com.knotworking.inhabit.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "habit")
data class HabitEntity(
    @PrimaryKey val id: UUID,
    val name: String,
    @ColumnInfo(name = "unit_label")val unitLabel: String
)
