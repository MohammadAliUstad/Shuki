package com.yugentech.shuki.ui.screens.notes

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yugentech.shuki.viewmodels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    noteId: Long,
    notesViewModel: NotesViewModel,
    navController: NavController
) {
    val note by notesViewModel.note.collectAsState()
    var isEditMode by remember { mutableStateOf(false) }
    var editedTitle by remember { mutableStateOf("") }
    var editedContent by remember { mutableStateOf("") }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var hasChanges by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    LaunchedEffect(noteId) {
        notesViewModel.getNote(noteId)
    }

    LaunchedEffect(note) {
        note?.let {
            if (!isEditMode) {
                editedTitle = it.title
                editedContent = it.content
            }
        }
    }

    LaunchedEffect(editedTitle, editedContent) {
        note?.let {
            hasChanges = editedTitle != it.title || editedContent != it.content
        }
    }

    fun onBackPressed() {
        if (hasChanges) {
            showDeleteConfirmation = true
        } else {
            navController.popBackStack()
        }
    }

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (isEditMode) "Edit Note" else "Note Details",
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
                    if (isEditMode) {
                        IconButton(
                            onClick = {
                                if (editedTitle.isNotBlank() || editedContent.isNotBlank()) {
                                    note?.let { currentNote ->
                                        notesViewModel.updateNote(
                                            currentNote.copy(
                                                title = editedTitle.trim(),
                                                content = editedContent.trim()
                                            )
                                        )
                                    }
                                    isEditMode = false
                                    hasChanges = false
                                }
                            },
                            enabled = hasChanges && editedTitle.isNotBlank()
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = "Save",
                                tint = if (hasChanges && editedTitle.isNotBlank())
                                    colorScheme.primary
                                else
                                    colorScheme.onSurfaceVariant
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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            if (isEditMode) {
                TextField(
                    value = editedTitle,
                    onValueChange = { editedTitle = it },
                    placeholder = {
                        Text(
                            "Title",
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
                    value = editedContent,
                    onValueChange = { editedContent = it },
                    placeholder = {
                        Text(
                            "Start typing...",
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
                note?.let { currentNote ->
                    Text(
                        text = currentNote.title,
                        style = typography.titleLarge,
                        color = colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = currentNote.content,
                        style = typography.bodyLarge,
                        color = colorScheme.onBackground
                    )
                } ?: Text(
                    text = "Loading note...",
                    style = typography.bodyLarge,
                    color = colorScheme.onSurfaceVariant
                )
            }
        }
    }

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Discard changes?", style = typography.titleLarge) },
            text = {
                Text(
                    "You have unsaved changes. Are you sure you want to discard them?",
                    style = typography.bodyLarge
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteConfirmation = false
                        navController.popBackStack()
                    }
                ) {
                    Text("Discard", color = colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("Keep editing")
                }
            }
        )
    }
}