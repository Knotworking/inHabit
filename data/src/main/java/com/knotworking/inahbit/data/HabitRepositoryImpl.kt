package com.knotworking.inahbit.data

import com.knotworking.inhabit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HabitRepositoryImpl : HabitRepository {
    override fun getHabits(): Flow<List<String>> = flow {
        emit(listOf("habit 1", "habit 2"))
    }
}