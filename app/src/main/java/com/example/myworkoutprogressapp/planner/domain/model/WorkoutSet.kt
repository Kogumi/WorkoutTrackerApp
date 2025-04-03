package com.example.myworkoutprogressapp.planner.domain.model

import androidx.room.Embedded
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
    val workoutDayId: Long = 0,
    val exerciseId: Long = 0,
    val setNumber: Int = 0,
    @Embedded
    val repRange: RepRange = RepRange(),
    val targetWeight: Float = 0f,
)


data class RepRange(
    val min: Int = 0,
    val max: Int = 0
)
