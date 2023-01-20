package com.knotworking.inhabit.presentation.add

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
class AddHabitEntryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getHabitUseCase: GetHabitUseCase,
    private val addHabitEntryUseCase: AddHabitEntryUseCase,
) : BaseViewModel() {
    private val habitId =
        UUID.fromString(checkNotNull(savedStateHandle.get<String>(HabitDetail.habitIdArg)))

    val addHabitEntryViewStateFlow: StateFlow<AddHabitEntryViewState>
        get() = _addHabitEntryViewStateFlow

    private var _addHabitEntryViewStateFlow = MutableStateFlow(AddHabitEntryViewState())

    override val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        _addHabitEntryViewStateFlow.value = _addHabitEntryViewStateFlow.value.copy(hasError = true)
    }

    init {
        getHabit()
    }

    private fun getHabit() {
        launchInViewModelScope {
            getHabitUseCase(habitId).onStart {
                _addHabitEntryViewStateFlow.value = AddHabitEntryViewState(loading = true)
            }.catch {
                _addHabitEntryViewStateFlow.value = AddHabitEntryViewState(hasError = true)
            }.collect { result ->
                result.onSuccess { habit ->
                    _addHabitEntryViewStateFlow.value =
                        AddHabitEntryViewState(habit = habit.toDisplayable())
                }.onFailure {
                    _addHabitEntryViewStateFlow.value = AddHabitEntryViewState(hasError = true)
                }
            }
        }
    }

    fun addEntry(unitCount: Int) {
        launchInViewModelScope {
            val newHabitEntry = createHabitEntry(habitId = habitId, unitCount = unitCount)
            addHabitEntryUseCase(newHabitEntry).collect { addHabitEntryResult ->
                addHabitEntryResult.onSuccess {
                    Log.d("AddHabitEntryViewModel", "New habit entry added")
                }.onFailure {
                    Log.e("AddHabitEntryViewModel", "Failed to add habit entry", it)
                }
            }
        }
    }
}