package com.example.myworkoutprogressapp.planner.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myworkoutprogressapp.planner.presentation.exerciseScreen.exerciseListScreen.ExerciseListScreen
import com.example.myworkoutprogressapp.planner.presentation.exerciseScreen.ExerciseScreenViewModel
import com.example.myworkoutprogressapp.planner.presentation.dayScreen.DayScreen
import com.example.myworkoutprogressapp.planner.presentation.dayScreen.DayViewModel
import com.example.myworkoutprogressapp.planner.presentation.exerciseScreen.addExerciseScreen.AddExerciseScreen
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
                    ) {
                        composable(route = Screen.PlansScreen.route) {
                            val viewModel = hiltViewModel<PlanViewModel>()
                            PlanScreen(navController = navController, viewModel = viewModel)
                        }

                        composable(
                            route = Screen.DaysScreen.route + "/{planId}",
                            arguments = listOf(
                                navArgument(name = "planId") {
                                    type = NavType.LongType
                                    defaultValue = 0L
                                }
                            )
                        ) {
                            val viewModel = hiltViewModel<DayViewModel>()
                            DayScreen(navController = navController, viewModel = viewModel)
                        }

                        navigation(
                            startDestination = Screen.ExerciseListScreen.route + "/{dayId}",
                            route = "exercise_graph"
                        ) {
                            composable(
                                route = Screen.ExerciseListScreen.route + "/{dayId}",
                                arguments = listOf(
                                    navArgument(name = "dayId") {
                                        type = NavType.LongType
                                    }
                                )
                            ) { backStackEntry ->
                                val parentEntry = remember(backStackEntry) {
                                    navController.getBackStackEntry("exercise_graph")
                                }
                                val viewModel = hiltViewModel<ExerciseScreenViewModel>(parentEntry)
                                ExerciseListScreen(navController, viewModel)
                            }

                            composable(
                                route = Screen.AddExerciseScreen.route
                            ) { backStackEntry ->
                                val parentEntry = remember(backStackEntry) {
                                    navController.getBackStackEntry("exercise_graph")
                                }
                                val viewModel = hiltViewModel<ExerciseScreenViewModel>(parentEntry)
                                AddExerciseScreen(navController, viewModel)
                            }

                            composable(
                                route = Screen.ExerciseDetailsScreen.route + "/{dayId}/{exerciseId}",
                                arguments = listOf(
                                    navArgument(name = "dayId") { type = NavType.LongType },
                                    navArgument(name = "exerciseId") { type = NavType.LongType }
                                )
                            ) {
                                // Same: get parentEntry if you want shared viewmodel
                            }
                        }
                    }

                }
            }
        }
    }
}
