package com.knotworking.inhabit.domain.usecase

import app.cash.turbine.test
import com.knotworking.inhabit.domain.generateTestHabits
import com.knotworking.inhabit.domain.repository.HabitRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetHabitsUseCaseTest {
    @RelaxedMockK
    private lateinit var habitRepository: HabitRepository

    private lateinit var objectUnderTest: GetHabitsUseCase

    @BeforeEach
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
                expected = Result.success(testHabits),
                actual = result
            )
            awaitComplete()
        }

        // verify
        coVerify { habitRepository.getHabits() }
    }

    @Test
    fun `should rethrow if repository throws CancellationException`() = runTest {
        // prepare
        coEvery { habitRepository.getHabits() } throws CancellationException()

        // execute-verify
        assertThrows<CancellationException> {
            objectUnderTest.invoke()
        }
    }

    @Test
    fun `should return failure if repository throws other Exception`() = runTest {
        // prepare
        val testException = Exception("test")
        coEvery { habitRepository.getHabits() } throws testException

        // execute
        assertThrows<Exception> {
            objectUnderTest.invoke().test {
                val result = awaitItem()

                assertEquals(
                    expected = Result.failure(testException),
                    actual = result
                )
            }
        }
    }

    private fun setUpGetHabitsUseCase() {
        objectUnderTest = GetHabitsUseCase {
            getHabits(habitRepository)
        }
    }
}