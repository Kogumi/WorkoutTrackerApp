package com.example.myworkoutprogressapp.planner.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myworkoutprogressapp.planner.domain.model.DayWithExercises
import com.example.myworkoutprogressapp.planner.domain.model.Exercise
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDayExercise
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutSet
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addSetToExercise(item: WorkoutSet): Long

    @Update
    suspend fun updateWorkoutSet(item: WorkoutSet)

    @Delete
    suspend fun deleteSetFromExercise(item: WorkoutSet)


    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addExercise(item: Exercise)

    @Update
    suspend fun updateExercise(item: Exercise)

    @Delete
    suspend fun deleteExercise(item: Exercise)


    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addExerciseToDay(item: WorkoutDayExercise)

    @Update
    suspend fun updateExerciseInDay(item: WorkoutDayExercise)

    @Delete
    suspend fun deleteExerciseFromDay(item: WorkoutDayExercise)


    @Transaction
    @Query("SELECT * from workout_days where workoutDayId = :dayId")
    fun getExercisesForDay(dayId: Long): Flow<DayWithExercises>
}