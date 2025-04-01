package com.example.myworkoutprogressapp.planner.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.Junction
import androidx.room.Relation

@Entity(tableName = "workout_day_exercises",
    primaryKeys = ["workoutDayId", "exerciseId"],
    foreignKeys = [
        ForeignKey(entity =
            WorkoutDay::class,
            parentColumns = ["workoutDayId"],
            childColumns = ["workoutDayId"],
            onDelete = CASCADE),
        ForeignKey(entity = Exercise::class,
            parentColumns = ["exerciseId"],
            childColumns = ["exerciseId"])
    ],
    indices = [Index("workoutDayId"), Index("exerciseId")])

data class WorkoutDayExercise(
    val workoutDayId: Long,
    val exerciseId: Long,
)

data class DayWithExercises(
    @Embedded val day: WorkoutDay = WorkoutDay(),
    @Relation(
        parentColumn = "workoutDayId",
        entityColumn = "exerciseId",
        associateBy = Junction(WorkoutDayExercise::class,
            parentColumn = "workoutDayId",
            entityColumn = "exerciseId")
    )
    val exercises: List<Exercise> = emptyList()
)
