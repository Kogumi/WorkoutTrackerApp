package com.example.myworkoutprogressapp.planner.domain.useCases.workoutPlanCases

class WorkoutPlanUseCases(
    val workoutPlanCrud: WorkoutPlanCrud,
    val workoutDayCrud: WorkoutDayCrud
) {
}