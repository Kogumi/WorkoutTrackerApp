package com.example.myworkoutprogressapp.planner.presentation.exerciseListScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkoutprogressapp.planner.domain.model.DayWithExercises
import com.example.myworkoutprogressapp.planner.domain.model.Exercise
import com.example.myworkoutprogressapp.planner.domain.model.WorkoutDay
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
class ExerciseListViewModel @Inject constructor(
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

    var isCreateVisible = mutableStateOf(false)
        private set

    private var editedExercise: Exercise? = null

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

    fun onEvent(event: ExerciseListEvent){
        when(event){
            is ExerciseListEvent.invokeAdd ->{
                isCreateVisible.value = true
            }
            is ExerciseListEvent.addExercise -> {

            }
        }
    }
}