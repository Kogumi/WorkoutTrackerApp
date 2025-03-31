package com.example.myworkoutprogressapp.planner.presentation.planScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutPlan
import com.example.myworkoutprogressapp.planner.domain.useCases.workoutPlanCases.WorkoutPlanUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val planUseCases: WorkoutPlanUseCases
): ViewModel(){

    private val _plansFlow = MutableStateFlow<List<WorkoutPlan>>(emptyList())
    val plans: StateFlow<List<WorkoutPlan>> = _plansFlow

    var planName = mutableStateOf("")
        private set
    private var editedPlan: WorkoutPlan? = null

    var isEditDialogVisible = mutableStateOf(false)
        private set
    var isDeleteDialogVisible = mutableStateOf(false)
        private set
    var isCreateDialogVisible = mutableStateOf(false)
        private set


    init{
        viewModelScope.launch {
            planUseCases.workoutPlanCrud.getPlans()
                .collect{ notes -> _plansFlow.value = notes }
        }
    }

    fun onEvent(event: PlanEvent){
        when(event){
            is PlanEvent.EnteredTitle ->{
                planName.value = event.title
            }
            is PlanEvent.DeletePlan -> {
                viewModelScope.launch{
                    planUseCases.workoutPlanCrud.deletePlan(event.plan)
                }
            }
            is PlanEvent.EditPlan -> {
                editedPlan?.let { plan ->
                    val newPlan = plan.copy(
                        name = planName.value
                    )
                    viewModelScope.launch {
                        planUseCases.workoutPlanCrud.updatePlan(newPlan)
                    }
                }
                editedPlan = null
            }
            is PlanEvent.InvokeEdit -> {
                planName.value = event.plan.name
                editedPlan = event.plan
                isEditDialogVisible.value = true
            }
            is PlanEvent.AddPlan -> {
                val newPlan = WorkoutPlan(
                    name = planName.value
                )
                viewModelScope.launch {
                    planUseCases.workoutPlanCrud.addPlan(newPlan)
                }
                planName.value = ""

            }
            is PlanEvent.DismissDialog -> {
                isDeleteDialogVisible.value = false
                isEditDialogVisible.value = false
                isCreateDialogVisible.value = false
            }
            is PlanEvent.InvokeCreate -> {
                isCreateDialogVisible.value = true
            }
        }
    }
}