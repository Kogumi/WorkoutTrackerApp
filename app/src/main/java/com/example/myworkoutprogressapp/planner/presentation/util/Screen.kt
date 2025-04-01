package com.example.myworkoutprogressapp.planner.presentation.util

sealed class Screen(val route: String) {
    object PlansScreen: Screen("plans_screen")
    object DaysScreen: Screen("days_screen")
    object ExerciseListScreen: Screen("exercise_list_screen")
    object ExerciseDetailsScreen: Screen("exercise_details_screen")
}