package com.example.myworkoutprogressapp.planner.presentation.exerciseScreen.addExerciseScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.myworkoutprogressapp.planner.presentation.exerciseScreen.ExerciseScreenEvent
import com.example.myworkoutprogressapp.planner.presentation.exerciseScreen.ExerciseScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseScreen(
    navController: NavController,
    viewModel: ExerciseScreenViewModel
) {
    val exerciseName = viewModel.exerciseName.value
    val notes = viewModel.exerciseNotes.value

    val day = viewModel.day.value

    var expanded by remember{mutableStateOf(false)}
    var textFieldSize by remember{ mutableStateOf(Size.Zero)}
    val options = List(10){ "Option ${it+1}" }

    val workoutSetList = viewModel.workoutSetList

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.secondary
                ),
                title = {
                    Text(day.name,
                        style = MaterialTheme.typography.bodyLarge)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxWidth()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())) {
            Box( modifier = Modifier.align(Alignment.CenterHorizontally)){
                TextField(
                    value = exerciseName,
                    onValueChange = {title  -> viewModel.onEvent(ExerciseScreenEvent.ChangeTitle(title))
                                    expanded = true},
                    label = { Text("Exercise name") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded,
                                        modifier = Modifier.matchParentSize() )},
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    properties = PopupProperties(focusable = false),
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(with(LocalDensity.current){ textFieldSize.width.toDp()})
                ) { options.forEach{ option ->
                    DropdownMenuItem(
                        text = { Text(option,
                            style = MaterialTheme.typography.bodyMedium) },
                        onClick = { viewModel.onEvent(ExerciseScreenEvent.ChangeTitle(option)) },
                    )
                }}
            }
            Spacer(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(32.dp))
            Text("Repetitions",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground)
            Column(modifier = Modifier.align(Alignment.CenterHorizontally).wrapContentSize()) {
                workoutSetList.forEachIndexed {index, set ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            "${index + 1}. ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.align(Alignment.CenterVertically))
                        TextField(
                            modifier = Modifier.width(128.dp),
                            value = if(set.repRange.min != 0){
                                set.repRange.min.toString()
                            } else {
                                ""
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            onValueChange = {
                                val newValue = it.toIntOrNull() ?: 0
                                viewModel.onEvent(
                                    ExerciseScreenEvent.ChangeRep(
                                    index = index,
                                    isMin = true,
                                    newValue = newValue
                                ))
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            modifier = Modifier.width(128.dp),
                            value = if(set.repRange.max != 0){
                                set.repRange.max.toString()
                            } else {
                                ""
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            onValueChange = {
                                val newValue = it.toIntOrNull() ?: 0
                                viewModel.onEvent(
                                    ExerciseScreenEvent.ChangeRep(
                                    index = index,
                                    isMin = false,
                                    newValue = newValue
                                ))
                            }
                        )
                        IconButton(
                            onClick = {
                                viewModel.onEvent(ExerciseScreenEvent.DeleteSet(set))
                            },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) { Icon(imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            modifier = Modifier.size(32.dp))}
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(modifier = Modifier.align(Alignment.End),
                    onClick = {
                    viewModel.onEvent(ExerciseScreenEvent.AddSet)
                }){
                    Text("Add set")
                }
            }
            Column(modifier = Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp)) {
                Text(
                    "Notes",
                    modifier = Modifier.align(Alignment.Start),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextField(value = notes,
                    onValueChange = { viewModel.onEvent(ExerciseScreenEvent.NotesChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                        .height(256.dp),
                    textStyle = MaterialTheme.typography.bodyMedium)
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.onEvent(ExerciseScreenEvent.AddExercise)
                }
            ) {
                Text("Add exercise",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}