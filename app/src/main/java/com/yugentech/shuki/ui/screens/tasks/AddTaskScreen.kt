package com.yugentech.shuki.ui.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yugentech.shuki.data.tasks.Task
import com.yugentech.shuki.viewmodels.TasksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    tasksViewModel: TasksViewModel,
    navController: NavController
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isCompleted by remember { mutableStateOf(false) }

    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "New Task",
                        style = typography.titleLarge,
                        color = colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (title.isNotBlank()) {
                                val task = Task(
                                    title = title.trim(),
                                    description = description.trim(),
                                    isCompleted = isCompleted
                                )
                                tasksViewModel.addTask(task)
                                navController.popBackStack()
                            }
                        },
                        enabled = title.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "Save",
                            tint = if (title.isNotBlank())
                                colorScheme.primary else colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.background,
                    titleContentColor = colorScheme.onBackground
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(colorScheme.background)
                .padding(16.dp)
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
                    style = typography.bodyMedium,
                    color = colorScheme.onBackground,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = {
                    Text(
                        text = "Title",
                        style = typography.bodyLarge,
                        color = colorScheme.onSurfaceVariant
                    )
                },
                textStyle = typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.background,
                    unfocusedContainerColor = colorScheme.background,
                    focusedTextColor = colorScheme.onBackground,
                    unfocusedTextColor = colorScheme.onBackground,
                    cursorColor = colorScheme.primary,
                    focusedIndicatorColor = colorScheme.primary,
                    unfocusedIndicatorColor = colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                placeholder = {
                    Text(
                        text = "Start typing...",
                        style = typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant
                    )
                },
                textStyle = typography.bodyMedium,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.background,
                    unfocusedContainerColor = colorScheme.background,
                    focusedTextColor = colorScheme.onBackground,
                    unfocusedTextColor = colorScheme.onBackground,
                    cursorColor = colorScheme.primary,
                    focusedIndicatorColor = colorScheme.primary,
                    unfocusedIndicatorColor = colorScheme.onSurfaceVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = Int.MAX_VALUE
            )
        }
    }
}