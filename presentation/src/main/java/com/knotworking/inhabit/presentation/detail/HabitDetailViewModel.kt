package com.knotworking.inhabit.presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.knotworking.inhabit.domain.usecase.AddHabitEntryUseCase
import com.knotworking.inhabit.domain.usecase.GetHabitUseCase
import com.knotworking.inhabit.presentation.BaseViewModel
import com.knotworking.inhabit.presentation.common.model.createHabitEntry
import com.knotworking.inhabit.presentation.common.model.toDisplayable
import com.knotworking.inhabit.presentation.navigation.HabitDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HabitDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getHabitUseCase: GetHabitUseCase,
    private val addHabitEntryUseCase: AddHabitEntryUseCase,
) : BaseViewModel() {

    private val habitId =
        UUID.fromString(checkNotNull(savedStateHandle.get<String>(HabitDetail.habitIdArg)))

    val habitDetailViewStateFlow: StateFlow<HabitDetailViewState>
        get() = _habitDetailStateFlow
    private var _habitDetailStateFlow = MutableStateFlow(HabitDetailViewState())

    override val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        _habitDetailStateFlow.value = _habitDetailStateFlow.value.copy(hasError = true)
    }

    init {
        getHabit()
    }

    private fun getHabit() {
        launchInViewModelScope {
            getHabitUseCase(habitId).onStart {
                _habitDetailStateFlow.value = HabitDetailViewState(loading = true)
            }.catch {
                _habitDetailStateFlow.value = HabitDetailViewState(hasError = true)
            }.collect { result ->
                result.onSuccess { habit ->
                    _habitDetailStateFlow.value =
                        HabitDetailViewState(habit = habit.toDisplayable())
                }.onFailure {
                    _habitDetailStateFlow.value = HabitDetailViewState(hasError = true)
                }
            }
        }
    }

    fun addEntry() {
        launchInViewModelScope {
            val newHabitEntry = createHabitEntry(habitId)
            addHabitEntryUseCase(newHabitEntry).collect { addHabitEntryResult ->
                addHabitEntryResult.onSuccess {
                    Log.d("HabitDetailViewModel", "New habit entry added")
                }.onFailure {
                    Log.e("HabitDetailViewModel", "Failed to add habit entry", it)
                }
            }
        }
    }
}