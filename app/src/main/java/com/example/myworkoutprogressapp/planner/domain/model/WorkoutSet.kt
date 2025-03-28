package com.example.myworkoutprogressapp.planner.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "workout_set",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutDayExercise::class,
            parentColumns = ["workoutDayId", "exerciseId"],
            childColumns = ["workoutDayId", "exerciseId"]
        )
    ],
    indices = [Index(value = ["workoutDayId" ,"exerciseId"])])

data class WorkoutSet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val workoutDayId: Long,
    val exerciseId: Long,
    val setNumber: Int,
    val targetRepsMin: Int,
    val targetRepsMax: Int,
    val targetWeight: Float = 0f,
)
