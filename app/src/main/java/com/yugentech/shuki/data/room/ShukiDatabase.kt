package com.yugentech.shuki.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yugentech.shuki.data.notes.Note
import com.yugentech.shuki.data.notes.NotesDao
import com.yugentech.shuki.data.tasks.Task
import com.yugentech.shuki.data.tasks.TasksDao

@Database(
    entities = [
        Note::class,
        Task::class
    ], version = 1
)

abstract class ShukiDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun tasksDao(): TasksDao
}