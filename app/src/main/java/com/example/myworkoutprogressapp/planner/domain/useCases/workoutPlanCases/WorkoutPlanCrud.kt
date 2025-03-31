package com.example.myworkoutprogressapp.planner.domain.useCases.workoutPlanCases

import com.example.myworkoutprogressapp.planner.data.repositories.WorkoutPlanRepository
import com.example.myworkoutprogressapp.planner.domain.exceptions.InvalidPlanException
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutPlan
import kotlinx.coroutines.flow.Flow

class WorkoutPlanCrud(
    private val repository: WorkoutPlanRepository
) {
    @Throws(InvalidPlanException::class)
    suspend fun addPlan(plan: WorkoutPlan): Long{
        if(plan.name.isBlank()){
            throw(InvalidPlanException("You must include a name for an exercise plan"))
        }
        return repository.addPlan(plan)
    }

    @Throws(InvalidPlanException::class)
    suspend fun updatePlan(plan: WorkoutPlan){
        if(plan.name.isBlank()){
            throw(InvalidPlanException("Exercise plan must have a name"))
        }
        repository.updatePlan(plan)
    }

    suspend fun deletePlan(plan: WorkoutPlan){
        repository.deletePlan(plan)
    }

    fun getPlans(): Flow<List<WorkoutPlan>>{
        return repository.getPlans()
    }

}