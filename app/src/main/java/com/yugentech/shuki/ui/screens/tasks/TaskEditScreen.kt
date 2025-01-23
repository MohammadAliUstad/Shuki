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
fun TaskEditScreen(
    taskId: Long,
    tasksViewModel: TasksViewModel,
    navController: NavController
) {
    val task by tasksViewModel.task.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isCompleted by remember { mutableStateOf(false) }
    var hasChanges by remember { mutableStateOf(false) }
    var showDiscardDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    // Fetch task when screen loads
    LaunchedEffect(taskId) {
        tasksViewModel.getTask(taskId)
    }

    // Initialize fields when task is loaded
    LaunchedEffect(task) {
        task?.let {
            title = it.title
            description = it.description
            isCompleted = it.isCompleted
        }
    }

    // Track changes
    LaunchedEffect(title, description, isCompleted) {
        task?.let {
            hasChanges = title != it.title ||
                    description != it.description ||
                    isCompleted != it.isCompleted
        }
    }

    fun onBackPressed() {
        if (hasChanges) {
            showDiscardDialog = true
        } else {
            navController.popBackStack()
        }
    }

    if (showDiscardDialog) {
        AlertDialog(
            onDismissRequest = { showDiscardDialog = false },
            title = {
                Text(
                    "Discard changes?",
                    style = typography.titleLarge
                )
            },
            text = {
                Text(
                    "You have unsaved changes. Are you sure you want to discard them?",
                    style = typography.bodyLarge
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDiscardDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        "Discard",
                        color = colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDiscardDialog = false }) {
                    Text("Keep editing")
                }
            }
        )
    }

    task?.let { currentTask ->  // Add null safety check
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Edit Task",
                                style = typography.titleLarge
                            )
                            if (hasChanges) {
                                Text(
                                    text = "Unsaved changes",
                                    style = typography.bodySmall,
                                    color = colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                if (title.isNotBlank()) {
                                    tasksViewModel.updateTask(
                                        currentTask.copy(  // Use currentTask
                                            title = title.trim(),
                                            description = description.trim(),
                                            isCompleted = isCompleted
                                        )
                                    )
                                    navController.popBackStack()
                                }
                            },
                            enabled = hasChanges && title.isNotBlank()
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = "Save",
                                tint = if (hasChanges && title.isNotBlank())
                                    colorScheme.primary
                                else
                                    colorScheme.onSurfaceVariant
                            )
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isCompleted,
                        onCheckedChange = { isCompleted = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorScheme.primary,
                            uncheckedColor = colorScheme.onSurfaceVariant
                        )
                    )
                    Text(
                        text = if (isCompleted) "Completed" else "In Progress",
                        style = typography.bodyLarge,
                        color = colorScheme.onBackground,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                TextField(
                    value = title,
                    onValueChange = { title = it },
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
                    value = description,
                    onValueChange = { description = it },
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
            }
        }
    }
}