package com.example.myworkoutprogressapp.planner.domain.useCases.workoutPlanCases

import com.example.myworkoutprogressapp.planner.data.repositories.WorkoutPlanRepository
import com.example.myworkoutprogressapp.planner.domain.exceptions.InvalidDayException
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDay
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutPlanWithDays
import kotlinx.coroutines.flow.Flow

class WorkoutDayCrud(
    private val repository: WorkoutPlanRepository
) {
    @Throws(InvalidDayException::class)
    suspend fun addDay(day: WorkoutDay): Long{
        if(day.name.isBlank()){
            throw(InvalidDayException("You must include a name for each day"))
        }
        return repository.addDay(day)
    }

    @Throws(InvalidDayException::class)
    suspend fun updateDay(day: WorkoutDay){
        if(day.name.isBlank()){
            throw(InvalidDayException("Exercise day must have a name"))
        }
        repository.updateDay(day)
    }

    suspend fun deleteDay(day: WorkoutDay){
        repository.deleteDay(day)
    }

    fun getDays(planId: Long): Flow<WorkoutPlanWithDays>{
        return repository.getDaysInPlan(planId)
    }


}