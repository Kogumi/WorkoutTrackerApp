package com.example.myworkoutprogressapp.planner.presentation.exerciseListScreen

sealed class ExerciseListEvent {
    object invokeAdd: ExerciseListEvent()
    object addExercise: ExerciseListEvent()
}