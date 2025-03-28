package com.example.myworkoutprogressapp.planner.domain.useCases.planCases

class WorkoutPlanUseCases(
    val workoutPlanCrud: WorkoutPlanCrud,
    val workoutDayCrud: WorkoutDayCrud
) {
}