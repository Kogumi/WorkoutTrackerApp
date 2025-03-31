package com.example.myworkoutprogressapp.planner.presentation.planScreen

import com.example.myworkoutprogressapp.planner.domain.model.WorkoutPlan

sealed class PlanEvent {
    data class EnteredTitle(val title: String): PlanEvent()
    data class DeletePlan(val plan: WorkoutPlan): PlanEvent()
    data class InvokeEdit(val plan: WorkoutPlan): PlanEvent()
    object AddPlan: PlanEvent()
    object EditPlan: PlanEvent()
    object DismissDialog: PlanEvent()
    object InvokeCreate: PlanEvent()

}