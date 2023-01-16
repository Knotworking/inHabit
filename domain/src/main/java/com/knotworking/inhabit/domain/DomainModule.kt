package com.knotworking.inhabit.domain

import com.knotworking.inhabit.domain.repository.HabitRepository
import com.knotworking.inhabit.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetHabitsUseCase(habitRepository: HabitRepository): GetHabitsUseCase {
        return GetHabitsUseCase {
            getHabits(habitRepository = habitRepository)
        }
    }

    @Provides
    fun provideAddHabitUseCase(habitRepository: HabitRepository): AddHabitUseCase {
        return AddHabitUseCase { habit ->
            addHabit(
                habitRepository = habitRepository,
                habit = habit
            )
        }
    }

    @Provides
    fun provideAddHabitEntryUseCase(habitRepository: HabitRepository): AddHabitEntryUseCase {
        return AddHabitEntryUseCase { habitEntry ->
            addHabitEntry(
                habitRepository = habitRepository,
                habitEntry = habitEntry
            )
        }
    }

    @Provides
    fun provideDeleteHabitUseCase(habitRepository: HabitRepository): DeleteHabitUseCase {
        return DeleteHabitUseCase { habitId ->
            deleteHabit(
                habitRepository = habitRepository,
                habitId = habitId
            )
        }
    }
}