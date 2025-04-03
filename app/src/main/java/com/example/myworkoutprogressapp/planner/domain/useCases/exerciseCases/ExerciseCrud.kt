package com.example.myworkoutprogressapp.planner.domain.useCases.exerciseCases

import com.example.myworkoutprogressapp.planner.data.repositories.ExerciseRepository
import com.example.myworkoutprogressapp.planner.domain.model.Exercise
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutSet

class ExerciseCrud(private val repository: ExerciseRepository) {
    suspend fun addExerciseToWorkout(
        exercise: Exercise,
        workoutDayId: Long,
        sets: List<WorkoutSet>
    ){
        repository.addExerciseToWorkout(exercise, workoutDayId, sets)
    }
}