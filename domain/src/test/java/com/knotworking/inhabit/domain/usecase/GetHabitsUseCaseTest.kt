package com.knotworking.inhabit.domain.usecase

import app.cash.turbine.test
import com.knotworking.inhabit.domain.generateTestHabits
import com.knotworking.inhabit.domain.repository.HabitRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetHabitsUseCaseTest {
    @RelaxedMockK
    private lateinit var habitRepository: HabitRepository

    private lateinit var objectUnderTest: GetHabitsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setUpGetHabitsUseCase()
    }

    @Test
    fun `should return success result`() = runTest {
        // prepare
        val testHabits = generateTestHabits()
        coEvery { habitRepository.getHabits() } returns flowOf(testHabits)

        // execute
        objectUnderTest.invoke().test {
            val result = awaitItem()

            assertEquals(
                /* expected = */ Result.success(testHabits),
                /* actual = */ result
            )
            awaitComplete()
        }

        // verify
        coVerify { habitRepository.getHabits() }
    }

    private fun setUpGetHabitsUseCase() {
        objectUnderTest = GetHabitsUseCase {
            getHabits(habitRepository)
        }
    }
}