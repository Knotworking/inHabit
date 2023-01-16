package com.knotworking.inhabit.home

import android.util.Log
import com.knotworking.inhabit.BaseViewModel
import com.knotworking.inhabit.domain.model.Habit
import com.knotworking.inhabit.domain.usecase.AddHabitEntryUseCase
import com.knotworking.inhabit.domain.usecase.AddHabitUseCase
import com.knotworking.inhabit.domain.usecase.DeleteHabitUseCase
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
    private val addHabitUseCase: AddHabitUseCase,
    private val addHabitEntryUseCase: AddHabitEntryUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase
) : BaseViewModel() {
    private var _count = 0
    val count: Int
        get() = _count

    val habitsViewState: StateFlow<HabitsViewState>
        get() = _habitsViewState
    private var _habitsViewState = MutableStateFlow(HabitsViewState())

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        //TODO handle error
        // also need a nice way to stop showing the error
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
                    Log.d("HomeViewModel", "${habits.size} habits loaded")
                }.onFailure {
                    Log.d("HomeViewModel", "getHabits result failure")
                }
            }
        }
    }

    fun addHabit() {
        launchInViewModelScope {
            val newHabit = Habit(
                id = UUID.randomUUID(),
                name = "Habit ${_habitsViewState.value.habits.size + 1}",
                entries = emptyList()
            )
            addHabitUseCase(newHabit).collect { addHabitResult ->
                addHabitResult.onSuccess {
                    Log.d("HomeViewModel", "New habit added")
                    addEntry(newHabit)
                }.onFailure {
                    Log.e("HomeViewModel", "Failed to add habit", it)
                }
            }
        }
    }

    fun addEntry(habit: Habit) {
        launchInViewModelScope {
            val newHabitEntry = Habit.Entry(
                id = UUID.randomUUID(),
                habitId = habit.id,
                timestamp = System.currentTimeMillis()
            )
            addHabitEntryUseCase(newHabitEntry).collect { addHabitEntryResult ->
                addHabitEntryResult.onSuccess {
                    Log.d("HomeViewModel", "New habit entry added")
                }.onFailure {
                    Log.e("HomeViewModel", "Failed to add habit entry", it)
                }
            }
        }
    }

    fun deleteHabit(habitId: UUID) {
        //Log.d("HomeViewModel", "Delete habit: $habitId")
        launchInViewModelScope {
            deleteHabitUseCase(habitId).collect { addHabitEntryResult ->
                addHabitEntryResult.onSuccess {
                    Log.d("HomeViewModel", "Habit deleted: $habitId")
                }.onFailure {
                    Log.e("HomeViewModel", "Failed delete habit: $habitId", it)
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