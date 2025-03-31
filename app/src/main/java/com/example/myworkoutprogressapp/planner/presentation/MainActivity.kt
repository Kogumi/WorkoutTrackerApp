package com.example.myworkoutprogressapp.planner.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myworkoutprogressapp.planner.presentation.dayScreen.DayScreen
import com.example.myworkoutprogressapp.planner.presentation.dayScreen.DayViewModel
import com.example.myworkoutprogressapp.planner.presentation.planScreen.PlanScreen
import com.example.myworkoutprogressapp.planner.presentation.planScreen.PlanViewModel
import com.example.myworkoutprogressapp.planner.presentation.util.Screen
import com.example.myworkoutprogressapp.ui.theme.MyWorkoutProgressAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWorkoutProgressAppTheme {
                Surface(color = MaterialTheme.colorScheme.background){
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.PlansScreen.route
                    ){
                        composable(route = Screen.PlansScreen.route){
                            val viewModel = hiltViewModel<PlanViewModel>()
                            PlanScreen(navController = navController,
                                viewModel = viewModel)
                        }
                        composable(
                            route = Screen.DaysScreen.route +
                                    "?planId={planId}",
                            arguments = listOf(
                                navArgument(
                                    name = "planId"
                                ){
                                    type = NavType.IntType
                                    defaultValue = 0
                                }
                            )
                        ){
                            val viewModel = hiltViewModel<DayViewModel>()
                            DayScreen(navController = navController,
                                viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
