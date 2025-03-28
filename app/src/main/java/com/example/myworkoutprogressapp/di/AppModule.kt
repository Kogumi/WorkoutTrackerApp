package com.example.myworkoutprogressapp.di

import android.app.Application
import androidx.room.Room
import com.example.myworkoutprogressapp.planner.data.WorkoutPlanRepository
import com.example.myworkoutprogressapp.planner.data.data_source.WorkoutDatabase
import com.example.myworkoutprogressapp.planner.domain.useCases.planCases.WorkoutPlanCrud
import com.example.myworkoutprogressapp.planner.domain.useCases.planCases.WorkoutDayCrud
import com.example.myworkoutprogressapp.planner.domain.useCases.planCases.WorkoutPlanUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWorkoutDatabase(app: Application): WorkoutDatabase{
        return Room.databaseBuilder(
            app,
            WorkoutDatabase::class.java,
            WorkoutDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideWorkoutRoutineRepository(db: WorkoutDatabase): WorkoutPlanRepository{
        return WorkoutPlanRepository(db.workoutPlanDao())
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: WorkoutPlanRepository): WorkoutPlanUseCases{
        return WorkoutPlanUseCases(
            WorkoutPlanCrud(repository),
            WorkoutDayCrud(repository)
        )
    }
}