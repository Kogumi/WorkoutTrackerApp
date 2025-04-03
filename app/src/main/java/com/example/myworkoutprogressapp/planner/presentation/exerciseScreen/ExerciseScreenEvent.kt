package com.example.myworkoutprogressapp.planner.presentation.exerciseScreen

import com.example.myworkoutprogressapp.planner.domain.model.WorkoutSet

sealed class ExerciseScreenEvent {
    data object AddExercise: ExerciseScreenEvent()
    data class ChangeTitle(val title: String): ExerciseScreenEvent()
    data class ChangeRep(val index: Int,
                         val isMin: Boolean,
                         val newValue: Int): ExerciseScreenEvent()
    data object AddSet: ExerciseScreenEvent()
    data class DeleteSet(val set: WorkoutSet): ExerciseScreenEvent()
    data class NotesChanged(val notes: String): ExerciseScreenEvent()
}