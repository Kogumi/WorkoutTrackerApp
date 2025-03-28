package com.example.myworkoutprogressapp.planner.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id:Long = 0,
    val name: String,
    val description: String? = null,
    val videoUrl: String? = null
)
