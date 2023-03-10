package com.knotworking.inhabit.presentation.home

import android.util.Log
import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.usecase.AddHabitUseCase
import com.knotworking.inhabit.domain.usecase.DeleteHabitUseCase
import com.knotworking.inhabit.domain.usecase.GetHabitsUseCase
import com.knotworking.inhabit.presentation.BaseViewModel
import com.knotworking.inhabit.presentation.common.model.toDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase
) : BaseViewModel() {
    val homeViewStateFlow: StateFlow<HomeViewState>
        get() = _homeViewStateFlow
    private var _homeViewStateFlow = MutableStateFlow(HomeViewState())

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        //TODO handle error
        // also need a nice way to stop showing the error
        _homeViewStateFlow.value = _homeViewStateFlow.value.copy(hasError = true)
    }

    init {
        //TODO look up where best to fetch data on launch
        getHabits()
    }

    private fun getHabits() {
        launchInViewModelScope {
            getHabitsUseCase().onStart {
                _homeViewStateFlow.value = HomeViewState(loading = true)
                Log.d("HomeViewModel", "Getting habits")
            }.catch {
                _homeViewStateFlow.value = HomeViewState(hasError = true)
                Log.d("HomeViewModel", "getHabits flow catch block")
            }.onCompletion {

            }.collect { result ->
                result.onSuccess { habits ->
                    _homeViewStateFlow.value =
                        HomeViewState(habits = habits.map { habit -> habit.toDisplayable() })
                    Log.d("HomeViewModel", "${habits.size} habits loaded")
                }.onFailure {
                    Log.d("HomeViewModel", "getHabits result failure")
                }
            }
        }
    }

    fun addHabit(name: String, unitLabel: String) {
        launchInViewModelScope {
            val newHabit = Habit(
                id = UUID.randomUUID(),
                name = name,
                unitLabel = unitLabel,
                entries = emptyList()
            )
            addHabitUseCase(newHabit).collect { addHabitResult ->
                addHabitResult.onSuccess {
                    Log.d("HomeViewModel", "New habit added")
                }.onFailure {
                    Log.e("HomeViewModel", "Failed to add habit", it)
                }
            }
        }
    }

    fun deleteHabit(habitId: UUID) {
        launchInViewModelScope {
            val result = deleteHabitUseCase(habitId)

            result.onSuccess {
                Log.d("HomeViewModel", "Habit deleted: $habitId")
            }.onFailure {
                Log.e("HomeViewModel", "Failed to delete habit: $habitId", it)
            }
        }
    }

}