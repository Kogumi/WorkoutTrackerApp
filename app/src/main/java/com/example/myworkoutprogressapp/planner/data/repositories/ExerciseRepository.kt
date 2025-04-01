package com.example.myworkoutprogressapp.planner.data.repositories

import com.example.myworkoutprogressapp.planner.data.data_source.ExerciseDao
import com.example.myworkoutprogressapp.planner.domain.model.DayWithExercises
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val exDao: ExerciseDao) {
    fun getExercisesForDay(dayId: Long): Flow<DayWithExercises>{
        return exDao.getExercisesForDay(dayId)
    }
}