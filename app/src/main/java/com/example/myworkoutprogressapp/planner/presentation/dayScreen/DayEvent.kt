package com.example.myworkoutprogressapp.planner.presentation.dayScreen

import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDay


sealed class DayEvent {
    data class EnteredTitle(val title: String): DayEvent()

    data object AddDay: DayEvent()
    data object EditDay: DayEvent()
    data class DeleteDay(val day: WorkoutDay): DayEvent()

    data class InvokeEdit(val day: WorkoutDay): DayEvent()
    data object InvokeCreate: DayEvent()
    data object DismissDialog: DayEvent()
}