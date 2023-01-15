package com.knotworking.inhabit.home

import androidx.lifecycle.viewModelScope
import com.knotworking.inhabit.BaseViewModel
import com.knotworking.inhabit.domain.usecase.GetHabitsUseCase
import com.knotworking.inhabit.model.HabitDisplayable
import com.knotworking.inhabit.model.toDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitsUseCase
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
            }.catch {
                _habitsViewState.value = HabitsViewState(hasError = true)
            }.onCompletion {

            }.collect { result ->
                result.onSuccess { habits ->
                    _habitsViewState.value =
                        HabitsViewState(habits = habits.map { habit -> habit.toDisplayable() })
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