package com.example.myworkoutprogressapp.planner.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myworkoutprogressapp.planner.domain.model.Exercise
import com.example.myworkoutprogressapp.planner.domain.model.ExerciseLog
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDay
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDayExercise
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutPlan
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutSet

@Database(entities = [
    Exercise::class,
    ExerciseLog::class,
    WorkoutDay::class,
    WorkoutPlan::class,
    WorkoutDayExercise::class,
    WorkoutSet::class
], version = 2,
    exportSchema = false)
abstract class WorkoutDatabase: RoomDatabase() {
    companion object{
        const val DATABASE_NAME = "workout_db"
    }
    abstract fun workoutPlanDao(): WorkoutPlanDao
    abstract fun workoutRoutineDao(): ExerciseDao
}