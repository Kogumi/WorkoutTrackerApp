package com.example.myworkoutprogressapp.planner.presentation.dayScreen

import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDay


sealed class DayEvent {
    data class EnteredTitle(val title: String): DayEvent()

    object AddDay: DayEvent()
    object EditDay: DayEvent()
    data class DeleteDay(val day: WorkoutDay): DayEvent()

    data class InvokeEdit(val day: WorkoutDay): DayEvent()
    object InvokeCreate: DayEvent()
    object DismissDialog: DayEvent()
}