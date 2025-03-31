package com.example.myworkoutprogressapp.planner.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import com.example.myworkoutprogressapp.planner.domain.model.ExerciseLog

@Dao
interface ExerciseLogDao {
   @Insert
   suspend fun addWorkoutLog(log: ExerciseLog): Long
}