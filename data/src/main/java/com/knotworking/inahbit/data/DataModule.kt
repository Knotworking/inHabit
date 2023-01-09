package com.knotworking.inahbit.data

import com.knotworking.inhabit.domain.repository.HabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideHabitRepository(): HabitRepository {
        return HabitRepositoryImpl()
    }
}