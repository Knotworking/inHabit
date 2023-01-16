package com.knotworking.inahbit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//TODO add entry relationship
@Entity(tableName = "habit")
data class HabitEntity(
    @PrimaryKey val id: UUID,
    val name: String
)
