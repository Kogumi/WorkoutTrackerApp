package com.example.myworkoutprogressapp.planner.presentation.util

sealed class Screen(val route: String) {
    data object PlansScreen: Screen("plans_screen")
    data object DaysScreen: Screen("days_screen")
    data object ExerciseListScreen: Screen("exercise_list_screen")
    data object ExerciseDetailsScreen: Screen("exercise_details_screen")
    data object AddExerciseScreen: Screen("add_exercise_screen")

    fun exerciseListRoute(dayId: Long) = "${ExerciseListScreen.route}/$dayId"
}