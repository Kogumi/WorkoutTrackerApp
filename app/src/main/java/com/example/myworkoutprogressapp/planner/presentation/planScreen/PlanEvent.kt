package com.example.myworkoutprogressapp.planner.presentation.planScreen

import com.example.myworkoutprogressapp.planner.domain.model.WorkoutPlan

sealed class PlanEvent {
    data class EnteredTitle(val title: String): PlanEvent()
    data class DeletePlan(val plan: WorkoutPlan): PlanEvent()
    data class InvokeEdit(val plan: WorkoutPlan): PlanEvent()
    data object AddPlan: PlanEvent()
    data object EditPlan: PlanEvent()
    data object DismissDialog: PlanEvent()
    data object InvokeCreate: PlanEvent()

}