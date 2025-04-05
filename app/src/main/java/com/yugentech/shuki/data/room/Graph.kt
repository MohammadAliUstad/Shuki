package com.yugentech.shuki.data.room

import android.app.Application
import androidx.room.Room
import com.yugentech.shuki.repositories.NotesRepository
import com.yugentech.shuki.repositories.TasksRepository

object Graph {
    private lateinit var database: ShukiDatabase
    private lateinit var application: Application

    fun provide(app: Application) {
        database = Room.databaseBuilder(
            app,
            ShukiDatabase::class.java,
            "Shuki.db"
        ).build()
        application = app
    }

    val notesRepository by lazy {
        NotesRepository(
            database.notesDao(),
            application = application
        )
    }

    val tasksRepository by lazy {
        TasksRepository(
            database.tasksDao(),
            application = application
        )
    }
}