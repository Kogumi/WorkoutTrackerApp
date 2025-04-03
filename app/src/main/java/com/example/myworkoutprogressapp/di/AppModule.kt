package com.example.myworkoutprogressapp.di

import android.app.Application
import androidx.room.Room
import com.example.myworkoutprogressapp.planner.data.repositories.WorkoutPlanRepository
import com.example.myworkoutprogressapp.planner.data.data_source.WorkoutDatabase
import com.example.myworkoutprogressapp.planner.data.repositories.ExerciseRepository
import com.example.myworkoutprogressapp.planner.domain.useCases.exerciseCases.ExerciseCrud
import com.example.myworkoutprogressapp.planner.domain.useCases.exerciseCases.ExerciseListCrud
import com.example.myworkoutprogressapp.planner.domain.useCases.exerciseCases.ExerciseUseCases
import com.example.myworkoutprogressapp.planner.domain.useCases.workoutPlanCases.WorkoutPlanCrud
import com.example.myworkoutprogressapp.planner.domain.useCases.workoutPlanCases.WorkoutDayCrud
import com.example.myworkoutprogressapp.planner.domain.useCases.workoutPlanCases.WorkoutPlanUseCases
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
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWorkoutPlanRepository(db: WorkoutDatabase): WorkoutPlanRepository {
        return WorkoutPlanRepository(db.workoutPlanDao())
    }

    @Provides
    @Singleton
    fun providePlanUseCases(repository: WorkoutPlanRepository): WorkoutPlanUseCases{
        return WorkoutPlanUseCases(
            WorkoutPlanCrud(repository),
            WorkoutDayCrud(repository)
        )
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(db: WorkoutDatabase): ExerciseRepository{
        return ExerciseRepository(db.exerciseDao())
    }

    @Provides
    @Singleton
    fun provideExerciseUseCases(repository: ExerciseRepository): ExerciseUseCases{
        return ExerciseUseCases(
            ExerciseListCrud(repository),
            ExerciseCrud(repository)
        )
    }

}