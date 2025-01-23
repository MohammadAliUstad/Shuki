package com.yugentech.shuki.repositories

import android.app.Application
import android.content.Context
import com.yugentech.shuki.data.tasks.Task
import com.yugentech.shuki.data.tasks.TasksDao
import com.yugentech.shuki.data.utils.DummyData
import kotlinx.coroutines.flow.Flow

class TasksRepository(
    private val tasksDao: TasksDao,
    application: Application
) {
    private val prefs = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val key = "isInitialized"

    suspend fun initializeDummyData() {
        if (!prefs.getBoolean(key, false)) {
            DummyData.dummyTasks.forEach { task ->
                tasksDao.addTask(task)
            }
            prefs.edit().putBoolean(key, true).apply()
        }
    }

    suspend fun addTask(task: Task) {
        tasksDao.addTask(task)
    }

    fun getTask(id: Long): Flow<Task> {
        return tasksDao.getTask(id)
    }

    fun getAllTasks(): Flow<List<Task>> {
        return tasksDao.getAllTasks()
    }

    suspend fun updateTask(task: Task) {
        tasksDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task)
    }
}