package com.yugentech.shuki.ui.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yugentech.shuki.viewmodels.TasksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    taskId: Long,
    tasksViewModel: TasksViewModel,
    navController: NavController
) {
    val task by tasksViewModel.task.collectAsState()
    var isEditMode by remember { mutableStateOf(false) }
    var editedTitle by remember { mutableStateOf("") }
    var editedDescription by remember { mutableStateOf("") }
    var editedIsCompleted by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    // Initialize by fetching the task
    LaunchedEffect(taskId) {
        tasksViewModel.getTask(taskId)
    }

    // Initialize edit fields when task is loaded
    LaunchedEffect(task) {
        task?.let {
            editedTitle = it.title
            editedDescription = it.description
            editedIsCompleted = it.isCompleted
        }
    }

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Delete Task", style = typography.titleLarge) },
            text = {
                Text(
                    "Are you sure you want to delete this task?",
                    style = typography.bodyLarge
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        task?.let { tasksViewModel.deleteTask(it) }
                        navController.popBackStack()
                    }
                ) {
                    Text("Delete", color = colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    task?.let { currentTask -> // Add null safety check
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = if (isEditMode) "Edit Task" else "Task Details",
                            style = typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (isEditMode) {
                                    isEditMode = false
                                    editedTitle = currentTask.title
                                    editedDescription = currentTask.description
                                    editedIsCompleted = currentTask.isCompleted
                                } else {
                                    navController.popBackStack()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        if (isEditMode) {
                            IconButton(
                                onClick = {
                                    if (editedTitle.isNotBlank()) {
                                        tasksViewModel.updateTask(
                                            currentTask.copy(
                                                title = editedTitle.trim(),
                                                description = editedDescription.trim(),
                                                isCompleted = editedIsCompleted
                                            )
                                        )
                                        isEditMode = false
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = "Save",
                                    tint = colorScheme.primary
                                )
                            }
                        } else {
                            Row {
                                IconButton(onClick = { isEditMode = true }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Edit,
                                        contentDescription = "Edit"
                                    )
                                }
                                IconButton(onClick = { showDeleteConfirmation = true }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Delete,
                                        contentDescription = "Delete"
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorScheme.background,
                        titleContentColor = colorScheme.onBackground
                    )
                )
            },
            containerColor = colorScheme.background
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                if (isEditMode) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = editedIsCompleted,
                            onCheckedChange = { editedIsCompleted = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = colorScheme.primary,
                                uncheckedColor = colorScheme.onSurfaceVariant
                            )
                        )
                        Text(
                            text = if (editedIsCompleted) "Completed" else "In Progress",
                            style = typography.bodyLarge,
                            color = colorScheme.onBackground,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    TextField(
                        value = editedTitle,
                        onValueChange = { editedTitle = it },
                        placeholder = {
                            Text(
                                "Task title",
                                style = typography.bodyLarge,
                                color = colorScheme.onSurfaceVariant
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.background,
                            unfocusedContainerColor = colorScheme.background,
                            focusedTextColor = colorScheme.onBackground,
                            unfocusedTextColor = colorScheme.onBackground,
                            cursorColor = colorScheme.primary,
                            focusedIndicatorColor = colorScheme.primary,
                            unfocusedIndicatorColor = colorScheme.onSurfaceVariant
                        ),
                        textStyle = typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = editedDescription,
                        onValueChange = { editedDescription = it },
                        placeholder = {
                            Text(
                                "Add description...",
                                style = typography.bodyMedium,
                                color = colorScheme.onSurfaceVariant
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.background,
                            unfocusedContainerColor = colorScheme.background,
                            focusedTextColor = colorScheme.onBackground,
                            unfocusedTextColor = colorScheme.onBackground,
                            cursorColor = colorScheme.primary,
                            focusedIndicatorColor = colorScheme.primary,
                            unfocusedIndicatorColor = colorScheme.onSurfaceVariant
                        ),
                        textStyle = typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        maxLines = Int.MAX_VALUE
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = currentTask.isCompleted,
                            onCheckedChange = {
                                tasksViewModel.updateTask(currentTask.copy(isCompleted = it))
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = colorScheme.primary,
                                uncheckedColor = colorScheme.onSurfaceVariant
                            )
                        )
                        Text(
                            text = if (currentTask.isCompleted) "Completed" else "In Progress",
                            style = typography.bodyLarge,
                            color = colorScheme.onBackground,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Text(
                        text = currentTask.title,
                        style = typography.titleLarge,
                        color = colorScheme.onBackground
                    )

                    if (currentTask.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = currentTask.description,
                            style = typography.bodyLarge,
                            color = colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}