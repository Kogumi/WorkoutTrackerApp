package com.example.myworkoutprogressapp.planner.presentation.dayScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDay
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutPlan
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutPlanWithDays
import com.example.myworkoutprogressapp.planner.domain.useCases.workoutPlanCases.WorkoutPlanUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val planUseCases: WorkoutPlanUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _daysFlow = MutableStateFlow(WorkoutPlanWithDays())

    val dayListFlow: StateFlow<List<WorkoutDay>> = _daysFlow.map{ it.days }
        .stateIn(viewModelScope,
            SharingStarted.Lazily,
            emptyList())

    var plan = mutableStateOf(WorkoutPlan())
        private set


    private var planId: Long = 0

    var dayName = mutableStateOf("")
        private set

    private var editedDay: WorkoutDay? = null

    var isEditDialogVisible = mutableStateOf(false)
        private set
    var isDeleteDialogVisible = mutableStateOf(false)
        private set
    var isCreateDialogVisible = mutableStateOf(false)
        private set


    init{
        savedStateHandle.get<Long>("planId")?.let{ planId ->
            this.planId = planId
            viewModelScope.launch {
                planUseCases.workoutDayCrud.getDays(planId)
                    .collect{ days -> _daysFlow.value = days
                        if(plan.value.name.isBlank()){
                            plan.value = days.plan
                        }
                    }
            }
        }
    }

    fun onEvent(event: DayEvent){
        when(event){
            is DayEvent.EnteredTitle ->{
                dayName.value = event.title
            }
            is DayEvent.DeleteDay -> {
                viewModelScope.launch{
                    planUseCases.workoutDayCrud.deleteDay(event.day)
                }
            }
            is DayEvent.EditDay -> {
                editedDay?.let { day ->
                    val newDay = day.copy(
                        name = dayName.value
                    )
                    viewModelScope.launch {
                        planUseCases.workoutDayCrud.updateDay(newDay)
                    }
                }
                editedDay = null
            }
            is DayEvent.InvokeEdit -> {
                dayName.value = event.day.name
                editedDay = event.day
                isEditDialogVisible.value = true
            }
            is DayEvent.AddDay -> {
                val newDay = WorkoutDay(
                    planId = planId,
                    name = dayName.value
                )
                viewModelScope.launch {
                    planUseCases.workoutDayCrud.addDay(newDay)
                }
                dayName.value = ""

            }
            is DayEvent.DismissDialog -> {
                isDeleteDialogVisible.value = false
                isEditDialogVisible.value = false
                isCreateDialogVisible.value = false
            }
            is DayEvent.InvokeCreate -> {
                isCreateDialogVisible.value = true
            }
        }
    }

}