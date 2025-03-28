package com.example.myworkoutprogressapp.planner.data

import com.example.myworkoutprogressapp.planner.data.data_source.WorkoutPlanDao
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDay
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutPlan
import kotlinx.coroutines.flow.Flow

class WorkoutPlanRepository(
    val planDao: WorkoutPlanDao
) {
    suspend fun addPlan(item: WorkoutPlan): Long{
        return planDao.addPlan(item)
    }
    suspend fun updatePlan(item: WorkoutPlan){
        planDao.updateWorkoutPlan(item)
    }
    suspend fun deletePlan(item: WorkoutPlan){
        planDao.deleteWorkoutPlan(item)
    }
    suspend fun addDay(item: WorkoutDay): Long{
        return planDao.addDayToPlan(item)
    }
    suspend fun updateDay(item: WorkoutDay){
        planDao.updateWorkoutDay(item)
    }
    suspend fun deleteDay(item: WorkoutDay){
        planDao.deleteWorkoutDay(item)
    }

    fun getPlans(): Flow<List<WorkoutPlan>>{
        return planDao.getPlans()
    }
    fun getDaysInPlan(planId: Long): Flow<List<WorkoutDay>>{
        return planDao.getDaysInPlan(planId = planId)
    }



}