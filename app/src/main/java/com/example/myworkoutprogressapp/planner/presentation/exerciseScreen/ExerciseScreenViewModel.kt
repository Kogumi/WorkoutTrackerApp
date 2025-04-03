package com.example.myworkoutprogressapp.planner.presentation.exerciseScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkoutprogressapp.planner.domain.model.DayWithExercises
import com.example.myworkoutprogressapp.planner.domain.model.Exercise
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDay
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutSet
import com.example.myworkoutprogressapp.planner.domain.useCases.exerciseCases.ExerciseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor(
    private val planUseCases: ExerciseUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _exerciseFlow = MutableStateFlow(DayWithExercises())

    val exerciseListFlow: StateFlow<List<Exercise>> = _exerciseFlow.map{ it.exercises }
        .stateIn(viewModelScope,
            SharingStarted.Lazily,
            emptyList())

    var day = mutableStateOf(WorkoutDay())
        private set

    var exerciseName = mutableStateOf("")
        private set
    var exerciseNotes = mutableStateOf("")
        private set

    val workoutSetList = mutableStateListOf<WorkoutSet>()


    init{
        savedStateHandle.get<Long>("dayId")?.let{ dayId ->
            println(dayId)
            viewModelScope.launch {
                planUseCases.ExerciseListCrud.getExercisesForDay(dayId)
                    .collect{ exercises -> _exerciseFlow.value = exercises
                        if(day.value.name.isBlank()){
                            day.value = exercises.day
                        }
                    }
            }
        }
    }

    fun onEvent(event: ExerciseScreenEvent){
        when(event){
            is ExerciseScreenEvent.ChangeTitle -> {
                exerciseName.value = event.title
            }
            is ExerciseScreenEvent.ChangeRep -> {
                val set = workoutSetList[event.index]
                val updatedRange = if(event.isMin){
                    set.repRange.copy(min = event.newValue)
                } else {
                    set.repRange.copy(max = event.newValue)
                }
                val updatedSet = set.copy(repRange = updatedRange)
                workoutSetList[event.index] = updatedSet
            }
            is ExerciseScreenEvent.AddSet -> {
                workoutSetList.add(WorkoutSet())
            }
            is ExerciseScreenEvent.DeleteSet -> {
                workoutSetList.remove(event.set)
            }
            is ExerciseScreenEvent.AddExercise ->{
                val exercise = Exercise(
                    name = exerciseName.value,
                    description = exerciseNotes.value
                )
                viewModelScope.launch {
                    planUseCases.ExerciseCrud.addExerciseToWorkout(
                        exercise = exercise,
                        workoutDayId = day.value.workoutDayId,
                        sets = workoutSetList.toList()
                    )
                }
            }
            is ExerciseScreenEvent.NotesChanged -> {
               exerciseNotes.value = event.notes
            }
        }
    }
}