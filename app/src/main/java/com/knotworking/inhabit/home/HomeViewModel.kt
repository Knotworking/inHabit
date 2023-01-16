package com.knotworking.inhabit.home

import android.util.Log
import com.knotworking.inhabit.BaseViewModel
import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.usecase.AddHabitUseCase
import com.knotworking.inhabit.domain.usecase.GetHabitsUseCase
import com.knotworking.inhabit.model.HabitDisplayable
import com.knotworking.inhabit.model.toDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val addHabitUseCase: AddHabitUseCase
) : BaseViewModel() {
    private var _count = 0
    val count: Int
        get() = _count

    val habitsViewState: StateFlow<HabitsViewState>
        get() = _habitsViewState
    private var _habitsViewState = MutableStateFlow(HabitsViewState())

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        // handle error
        _habitsViewState.value = _habitsViewState.value.copy(hasError = true)
    }

    init {
        //TODO look up where best to fetch data on launch
        getHabits()
    }

    fun getHabits() {
        launchInViewModelScope {
            getHabitsUseCase().onStart {
                _habitsViewState.value = HabitsViewState(loading = true)
                Log.d("HomeViewModel", "Getting habits")
            }.catch {
                _habitsViewState.value = HabitsViewState(hasError = true)
                Log.d("HomeViewModel", "getHabits flow catch block")
            }.onCompletion {

            }.collect { result ->
                result.onSuccess { habits ->
                    _habitsViewState.value =
                        HabitsViewState(habits = habits.map { habit -> habit.toDisplayable() })
                    Log.d("HomeViewModel", "Habits loaded")
                    //TODO don't do this here after adding proper "add habit" button
                    if (habits.isEmpty()) {
                        addHabit()
                    }
                }.onFailure {
                    Log.d("HomeViewModel", "getHabits result failure")
                }
            }
        }
    }

    suspend fun addHabit() {
        launchInViewModelScope {
            val newHabit = Habit(
                id = UUID.randomUUID(),
                name = "Exercise",
                entries = emptyList()
            )
            addHabitUseCase(newHabit).collect { addHabitResult ->
                addHabitResult.onSuccess {
                    Log.d("HomeViewModel", "New habit added")
                    getHabits()
                }.onFailure {
                    Log.e("HomeViewModel", "Failed to add habit", it)
                }
            }
        }
    }

    data class HabitsViewState(
        val hasError: Boolean = false,
        val loading: Boolean = false,
        val habits: List<HabitDisplayable> = listOf()
    )
}