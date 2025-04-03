package com.example.myworkoutprogressapp.planner.presentation.exerciseScreen.exerciseListScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myworkoutprogressapp.planner.presentation.components.GenericLazyList
import com.example.myworkoutprogressapp.planner.presentation.exerciseScreen.ExerciseScreenViewModel
import com.example.myworkoutprogressapp.planner.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListScreen (
    navController: NavController,
    viewModel: ExerciseScreenViewModel
) {
    val exerciseList = viewModel.exerciseListFlow.collectAsState()
    val day = viewModel.day.value

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
    floatingActionButton = {
        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = {
                navController.navigate(Screen.AddExerciseScreen.route)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add exercise"
            )
        }
    }
    ) { innerPadding ->
        GenericLazyList(
            modifier = Modifier.padding(innerPadding),
            items = exerciseList.value,
            onDelete = { exercise ->
            },
            onEdit = { exercise ->
            },
            onClick = { exercise ->
                navController.navigate(Screen.ExerciseDetailsScreen.route + "/${day.workoutDayId}/${exercise.exerciseId}")
            },
            getName = { it.name },
            getId = { it.exerciseId }
        )
    }
}