package com.knotworking.inhabit.domain

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [DomainModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetHabitsUseCase() : GetHabitsUseCase {
        return GetHabitsUseCase {
            getHabits()
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
//        @Binds
//        @Singleton
//        fun bindRocketRepository(impl: RocketRepositoryImpl): RocketRepository
    }
}