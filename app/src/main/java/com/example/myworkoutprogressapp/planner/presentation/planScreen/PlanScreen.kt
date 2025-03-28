package com.example.myworkoutprogressapp.planner.presentation.planScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
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
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.secondary
                ),
                title = {
                    Text("My workout app",
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.secondary,
                onClick = {
                    viewModel.onEvent(PlanEvent.InvokeCreate)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { innerPadding ->
        if(isCreateVisible.value){
           Dialog(onDismissRequest = {
               viewModel.onEvent(PlanEvent.DismissCreate)
               viewModel.onEvent(PlanEvent.EnteredTitle(""))
           },
               content = {
                   Card(modifier = Modifier
                       .wrapContentSize()
                       .padding(16.dp),
                       shape = RoundedCornerShape(16.dp)) {
                       Column(modifier = Modifier.padding(16.dp),
                           horizontalAlignment = Alignment.CenterHorizontally) {
                           TextField(modifier = Modifier.border(
                               width = 2.dp, color = MaterialTheme.colorScheme.secondary
                           ),
                               value = planName.value,
                               label = { Text("Enter Plan")},
                               onValueChange = {name ->
                                   viewModel.onEvent(PlanEvent.EnteredTitle(name))
                               })
                           Spacer(modifier = Modifier.height(26.dp))
                           Button(onClick = {
                               viewModel.onEvent(PlanEvent.AddPlan)
                               viewModel.onEvent(PlanEvent.DismissCreate)
                           }){
                               Text("Save Plan")
                           }
                       }
                   }
               })
        }
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
           items(planList.value) { workoutPlan ->
               ListElement(
                   text = workoutPlan.name,
                   id = workoutPlan.id,
                   onDelete = {
                       viewModel.onEvent(PlanEvent.DeletePlan(workoutPlan))
                   },
                   onEdit = {

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