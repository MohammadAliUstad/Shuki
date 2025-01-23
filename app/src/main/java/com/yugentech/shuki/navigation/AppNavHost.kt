@file:Suppress("DEPRECATION")

package com.yugentech.shuki.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.yugentech.shuki.ui.screens.AboutScreen
import com.yugentech.shuki.ui.screens.HomeScreen
import com.yugentech.shuki.ui.screens.notes.AddNoteScreen
import com.yugentech.shuki.ui.screens.notes.NoteDetailsScreen
import com.yugentech.shuki.ui.screens.notes.NoteEditScreen
import com.yugentech.shuki.ui.screens.tasks.AddTaskScreen
import com.yugentech.shuki.ui.screens.tasks.TaskDetailsScreen
import com.yugentech.shuki.ui.screens.tasks.TaskEditScreen
import com.yugentech.shuki.viewmodels.HomeViewModel
import com.yugentech.shuki.viewmodels.NotesViewModel
import com.yugentech.shuki.viewmodels.TasksViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    notesViewModel: NotesViewModel,
    tasksViewModel: TasksViewModel,
    homeViewModel: HomeViewModel
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route,
        enterTransition = { defaultEnterTransition() },
        exitTransition = { defaultExitTransition() },
        popEnterTransition = { defaultPopEnterTransition() },
        popExitTransition = { defaultPopExitTransition() }
    ) {
        composable(Screens.HomeScreen.route) {
            HomeScreen(
                homeViewModel = homeViewModel,
                tasksViewModel = tasksViewModel,
                navController = navController,
                notesViewModel = notesViewModel
            )
        }
        composable(Screens.AboutScreen.route) {
            AboutScreen(
                navController = navController
            )
        }
        composable(Screens.AddNoteScreen.route) {
            AddNoteScreen(
                notesViewModel,
                navController = navController
            )
        }
        composable(Screens.NoteDetailsScreen.route) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLong()
            if (noteId != null) {
                NoteDetailsScreen(
                    noteId,
                    notesViewModel,
                    navController
                )
            }
        }
        composable(Screens.NoteEditScreen.route) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLong()
            if (noteId != null) {
                NoteEditScreen(
                    noteId,
                    notesViewModel,
                    navController
                )
            }
        }
        composable(Screens.AddTaskScreen.route) {
            AddTaskScreen(
                tasksViewModel,
                navController
            )
        }
        composable(Screens.TaskDetailsScreen.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toLong()
            if (taskId != null) {
                TaskDetailsScreen(
                    taskId,
                    tasksViewModel,
                    navController
                )
            }
        }
        composable(Screens.TaskEditScreen.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toLong()
            if (taskId != null) {
                TaskEditScreen(
                    taskId,
                    tasksViewModel,
                    navController
                )
            }
        }
    }
}

private fun defaultEnterTransition(): EnterTransition {
    return slideInHorizontally(
        animationSpec = tween(300),
        initialOffsetX = { fullWidth -> fullWidth }
    ) + fadeIn(animationSpec = tween(300))
}

private fun defaultExitTransition(): ExitTransition {
    return slideOutHorizontally(
        animationSpec = tween(300),
        targetOffsetX = { fullWidth -> fullWidth }
    ) + fadeOut(animationSpec = tween(300))
}

private fun defaultPopEnterTransition(): EnterTransition {
    return slideInHorizontally(
        animationSpec = tween(300),
        initialOffsetX = { fullWidth -> -fullWidth }
    ) + fadeIn(animationSpec = tween(300))
}

private fun defaultPopExitTransition(): ExitTransition {
    return slideOutHorizontally(
        animationSpec = tween(300),
        targetOffsetX = { fullWidth -> -fullWidth }
    ) + fadeOut(animationSpec = tween(300))
}