package com.knotworking.inhabit.domain.repository

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getHabits() : Flow<List<String>>
}