package com.example.myworkoutprogressapp.planner.presentation.planScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myworkoutprogressapp.planner.presentation.components.CreationEditDialog
import com.example.myworkoutprogressapp.planner.presentation.components.ListElement
import com.example.myworkoutprogressapp.planner.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanScreen(
    navController: NavController,
    viewModel: PlanViewModel
) {
    val planList = viewModel.plans.collectAsState()

    val isEditVisible = viewModel.isEditDialogVisible
    val isDeleteVisible = viewModel.isDeleteDialogVisible
    val isCreateVisible = viewModel.isCreateDialogVisible

    val planName = viewModel.planName

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    Text("Workout Plans",
                        style = MaterialTheme.typography.bodyLarge)
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    viewModel.onEvent(PlanEvent.InvokeCreate)
                },
                modifier = Modifier.wrapContentWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add plan",
                    modifier = Modifier.size(32.dp))
            }
        }
    ) { innerPadding ->
        if(isCreateVisible.value){
            CreationEditDialog(
                textFieldValue = planName.value,
                textFieldLabel = "Enter plan title",
                buttonText = "Save plan",
                onDismissRequest = { viewModel.onEvent(PlanEvent.DismissDialog) },
                onValueChange = {value: String -> viewModel.onEvent(PlanEvent.EnteredTitle(value))},
                onButtonClick = { viewModel.onEvent(PlanEvent.AddPlan)
                                    viewModel.onEvent(PlanEvent.DismissDialog)}
            )
        }
        if(isEditVisible.value){
            CreationEditDialog(
                textFieldValue = planName.value,
                textFieldLabel = "Enter new plan name",
                buttonText = "Save plan",
                onDismissRequest = { viewModel.onEvent(PlanEvent.DismissDialog) },
                onValueChange = { value: String -> viewModel.onEvent(PlanEvent.EnteredTitle(value))},
                onButtonClick = { viewModel.onEvent(PlanEvent.EditPlan)
                                    viewModel.onEvent(PlanEvent.DismissDialog)}
            )
        }
        LazyColumn(modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(planList.value) { workoutPlan ->
               ListElement(
                   text = workoutPlan.name,
                   id = workoutPlan.id,
                   onDelete = {
                       viewModel.onEvent(PlanEvent.DeletePlan(workoutPlan))
                   },
                   onEdit = {
                       viewModel.onEvent(PlanEvent.InvokeEdit(workoutPlan))
                   },
                   onClick = {
                       navController.navigate(Screen.DaysScreen.route + "?planId=${workoutPlan.id}")
                   }
               )
           }
       }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewPlanScreen(){
}