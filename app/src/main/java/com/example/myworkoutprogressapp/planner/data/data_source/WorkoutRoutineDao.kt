package com.example.myworkoutprogressapp.planner.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myworkoutprogressapp.planner.domain.model.Exercise
import com.example.myworkoutprogressapp.planner.domain.model.ExerciseWithSets
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDayExercise
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutSet
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutRoutineDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addExerciseToDay(item: WorkoutDayExercise)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addSetToExercise(item: WorkoutSet): Long

    @Update
    suspend fun updateExercise(item: Exercise)

    @Update
    suspend fun updateWorkoutSet(item: WorkoutSet)

    @Delete
    suspend fun deleteExerciseFromDay(item: WorkoutDayExercise)

    @Delete
    suspend fun deleteSetFromExercise(item: WorkoutSet)

    @Query("SELECT * from workout_day_exercises WHERE workoutDayId = :id")
    fun getExercisesForDay(id: Long): Flow<List<WorkoutDayExercise>>

    @Transaction
    @Query("SELECT * FROM workout_day_exercises " +
            "WHERE workoutDayId = :workoutId AND exerciseId = :exerciseId")
    suspend fun getExerciseWithSets(workoutId: Long, exerciseId: Long): ExerciseWithSets



}