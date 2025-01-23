package com.yugentech.shuki.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yugentech.shuki.data.notes.NotesList
import com.yugentech.shuki.data.tasks.TasksList
import com.yugentech.shuki.navigation.Screens
import com.yugentech.shuki.viewmodels.HomeViewModel
import com.yugentech.shuki.viewmodels.NotesViewModel
import com.yugentech.shuki.viewmodels.TasksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    notesViewModel: NotesViewModel,
    tasksViewModel: TasksViewModel,
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    var selectedTab by remember { mutableIntStateOf(homeViewModel.selectedTab.value) }

    val notes by notesViewModel.notes.collectAsState()
    val tasks by tasksViewModel.tasks.collectAsState()


    val notesListState = rememberLazyListState()
    val tasksListState = rememberLazyListState()

    val isScrolled by remember {
        derivedStateOf {
            notesListState.firstVisibleItemIndex > 0 || tasksListState.firstVisibleItemScrollOffset > 0
        }
    }

    val appBarContainerColor by animateColorAsState(
        targetValue = if (isScrolled) colorScheme.surface else colorScheme.background,
        animationSpec = tween(300),
        label = "appBarColor"
    )

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Home,
                                contentDescription = "Shuki Logo",
                                tint = colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Shuki",
                                style = typography.titleLarge,
                                color = colorScheme.onBackground
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { navController.navigate("about") }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Info,
                                contentDescription = "About",
                                tint = colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = appBarContainerColor,
                        titleContentColor = colorScheme.onBackground
                    )
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = colorScheme.surface,
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        homeViewModel.setSelectedTab(0)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "Notes"
                        )
                    },
                    label = {
                        Text(
                            text = "Notes",
                            style = typography.labelMedium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorScheme.primary,
                        selectedTextColor = colorScheme.primary,
                        unselectedIconColor = colorScheme.onSurfaceVariant,
                        unselectedTextColor = colorScheme.onSurfaceVariant,
                        indicatorColor = colorScheme.surfaceVariant
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        homeViewModel.setSelectedTab(1)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "Tasks"
                        )
                    },
                    label = {
                        Text(
                            text = "Tasks",
                            style = typography.labelMedium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorScheme.primary,
                        selectedTextColor = colorScheme.primary,
                        unselectedIconColor = colorScheme.onSurfaceVariant,
                        unselectedTextColor = colorScheme.onSurfaceVariant,
                        indicatorColor = colorScheme.surfaceVariant
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (selectedTab == 0) {
                        navController.navigate(Screens.AddNoteScreen.route)
                    } else {
                        navController.navigate(Screens.AddTaskScreen.route)
                    }
                },
                containerColor = colorScheme.primaryContainer,
                contentColor = colorScheme.onPrimaryContainer,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = if (selectedTab == 0) "Add Note" else "Add Task"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorScheme.background)
        ) {
            AnimatedVisibility(
                visible = selectedTab == 0,
                enter = slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            ) {
                NotesList(
                    notes = notes,
                    listState = notesListState,
                    onNoteClick = { note ->
                        navController.navigate(Screens.NoteDetailsScreen.createRoute(note.id.toString()))
                    },
                    onEditClick = { note ->
                        navController.navigate(Screens.NoteEditScreen.createRoute(note.id.toString()))
                    },
                    onDeleteClick = { note ->
                        notesViewModel.deleteNote(note)
                    }
                )
            }

            AnimatedVisibility(
                visible = selectedTab == 1,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                )
            ) {
                TasksList(
                    tasks = tasks,
                    listState = tasksListState,
                    onTaskClick = { task ->
                        navController.navigate(Screens.TaskDetailsScreen.createRoute(task.id.toString()))
                    },
                    onToggleCompletion = { task ->
                        tasksViewModel.updateTask(task.copy(isCompleted = !task.isCompleted))
                    },
                    onDeleteClick = { task ->
                        tasksViewModel.deleteTask(task)
                    }
                )
            }
        }
    }
}