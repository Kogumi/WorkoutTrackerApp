package com.example.myworkoutprogressapp.planner.domain.useCases.exerciseCases

import com.example.myworkoutprogressapp.planner.data.repositories.ExerciseRepository
import com.example.myworkoutprogressapp.planner.domain.model.DayWithExercises
import kotlinx.coroutines.flow.Flow

class ExerciseListCrud(private val exerciseRepository: ExerciseRepository) {
    fun getExercisesForDay(dayId: Long): Flow<DayWithExercises>{
        return exerciseRepository.getExercisesForDay(dayId)
    }
}