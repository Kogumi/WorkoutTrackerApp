package com.example.myworkoutprogressapp.planner.presentation.dayScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.myworkoutprogressapp.planner.presentation.components.CreationEditDialog
import com.example.myworkoutprogressapp.planner.presentation.components.ListElement
import com.example.myworkoutprogressapp.planner.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayScreen(
    navController: NavController,
    viewModel: DayViewModel
) {
    val dayList = viewModel.dayListFlow.collectAsState()
    val plan = viewModel.plan.value

    val dayName = viewModel.dayName.value

    val isEditVisible = viewModel.isEditDialogVisible
    val isDeleteVisible = viewModel.isDeleteDialogVisible
    val isCreateVisible = viewModel.isCreateDialogVisible

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.secondary
                ),
                title = {
                    Text(plan.name,
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
                    viewModel.onEvent(DayEvent.InvokeCreate)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add plan"
                )
            }
        }
    ) { innerPadding ->
        if(isCreateVisible.value){
            CreationEditDialog(
                textFieldValue = dayName,
                textFieldLabel = "Enter plan title",
                buttonText = "Save plan",
                onDismissRequest = { viewModel.onEvent(DayEvent.DismissDialog) },
                onValueChange = {value: String -> viewModel.onEvent(DayEvent.EnteredTitle(value))},
                onButtonClick = { viewModel.onEvent(DayEvent.AddDay)
                    viewModel.onEvent(DayEvent.DismissDialog)}
            )
        }
        if(isEditVisible.value){
            CreationEditDialog(
                textFieldValue = dayName,
                textFieldLabel = "Enter new plan name",
                buttonText = "Save plan",
                onDismissRequest = { viewModel.onEvent(DayEvent.DismissDialog) },
                onValueChange = { value: String -> viewModel.onEvent(DayEvent.EnteredTitle(value))},
                onButtonClick = { viewModel.onEvent(DayEvent.EditDay)
                    viewModel.onEvent(DayEvent.DismissDialog)}
            )
        }
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(dayList.value) { workoutDay ->
                ListElement(
                    text = workoutDay.name,
                    id = workoutDay.workoutDayId,
                    onDelete = {
                        viewModel.onEvent(DayEvent.DeleteDay(workoutDay))
                    },
                    onEdit = {
                        viewModel.onEvent(DayEvent.InvokeEdit(workoutDay))
                    },
                    onClick = {
                        navController.navigate(Screen.DaysScreen.route + "?dayId=${workoutDay.workoutDayId}")
                    }
                )
            }
        }
    }
}
