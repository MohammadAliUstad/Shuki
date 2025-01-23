package com.yugentech.shuki.data.notes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.yugentech.shuki.ui.components.NoteCard

@Composable
fun NotesList(
    notes: List<Note>,
    listState: LazyListState,
    onNoteClick: (Note) -> Unit,
    onEditClick: (Note) -> Unit,
    onDeleteClick: (Note) -> Unit
) {
    LazyColumn(
        state = listState
    ) {
        items(
            items = notes,
            key = { it.id }
        ) { note ->
            NoteCard(
                note = note,
                onClick = { onNoteClick(note) },
                onEditClick = { onEditClick(note) },
                onDeleteClick = { onDeleteClick(note) }
            )
        }
    }
}