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
            parentColumns = ["id"],
            childColumns = ["workoutDayId"],
            onDelete = CASCADE),
        ForeignKey(entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"])
    ],
    indices = [Index("workoutDayId"), Index("exerciseId")])
data class WorkoutDayExercise(
    val workoutDayId: Long,
    val exerciseId: Long,
    val exerciseName: String
)

data class ExerciseWithSets(
    @Embedded val dayExercise: WorkoutDayExercise,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "id"
    )
    val exercise: Exercise,
    @Relation(
        parentColumn = "workoutDayId",
        entityColumn = "workoutDayId",
        associateBy = Junction(
            value = WorkoutSet::class,
            parentColumn = "workoutDayId",
            entityColumn = "exerciseId"
        )
    )
    val sets: List<WorkoutSet>
)