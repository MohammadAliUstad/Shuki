package com.yugentech.shuki.data.tasks

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.yugentech.shuki.ui.components.TaskCard

@Composable
fun TasksList(
    tasks: List<Task>,
    listState: LazyListState,
    onTaskClick: (Task) -> Unit,
    onToggleCompletion: (Task) -> Unit,
    onDeleteClick: (Task) -> Unit
) {
    LazyColumn(
        state = listState
    ) {
        items(
            items = tasks,
            key = { it.id }
        ) { task ->
            TaskCard(
                task = task,
                onClick = { onTaskClick(task) },
                onToggleCompletion = { onToggleCompletion(task) },
                onDeleteClick = { onDeleteClick(task) }
            )
        }
    }
}