package com.example.myworkoutprogressapp.planner.data.repositories

import com.example.myworkoutprogressapp.planner.data.data_source.ExerciseDao
import com.example.myworkoutprogressapp.planner.domain.model.DayWithExercises
import com.example.myworkoutprogressapp.planner.domain.model.Exercise
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutSet
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val exDao: ExerciseDao) {
    fun getExercisesForDay(dayId: Long): Flow<DayWithExercises>{
        return exDao.getExercisesForDay(dayId)
    }

    suspend fun addExerciseToWorkout(
        exercise: Exercise,
        workoutDayId: Long,
        sets: List<WorkoutSet>
    ){
        exDao.addExerciseToWorkout(exercise, workoutDayId, sets)
    }
}