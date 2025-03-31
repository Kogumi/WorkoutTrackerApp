package com.example.myworkoutprogressapp.planner.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "workout_days",
    foreignKeys = [ForeignKey(
        entity = WorkoutPlan::class,
        parentColumns = ["id"],
        childColumns = ["planId"],
        onDelete = CASCADE
    )],
    indices = [Index("planId")]
)
data class WorkoutDay(
    @PrimaryKey(autoGenerate = true) val workoutDayId:Long = 0,
    val planId: Long,
    val name: String
    )

data class WorkoutPlanWithDays(
    @Embedded
    val plan: WorkoutPlan = WorkoutPlan(),
    @Relation(
        parentColumn = "id",
        entityColumn = "planId"
    )
    val days: List<WorkoutDay> = emptyList()

)
