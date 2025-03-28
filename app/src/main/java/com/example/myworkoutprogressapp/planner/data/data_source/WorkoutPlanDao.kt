package com.example.myworkoutprogressapp.planner.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDay
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutPlan
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutPlanDao {
    @Insert
    suspend fun addPlan(plan: WorkoutPlan): Long

    @Insert
    suspend fun addDayToPlan(day: WorkoutDay): Long

    @Update
    suspend fun updateWorkoutPlan(plan: WorkoutPlan)

    @Update
    suspend fun updateWorkoutDay(day: WorkoutDay)

    @Delete
    suspend fun deleteWorkoutPlan(plan: WorkoutPlan)

    @Delete
    suspend fun deleteWorkoutDay(day: WorkoutDay)

    @Query("SELECT * FROM workout_plans")
    fun getPlans(): Flow<List<WorkoutPlan>>

    @Query("SELECT * FROM workout_days WHERE planId = :planId")
    fun getDaysInPlan(planId: Long): Flow<List<WorkoutDay>>
}