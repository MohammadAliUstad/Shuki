package com.yugentech.shuki.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yugentech.shuki.data.room.Graph
import com.yugentech.shuki.data.tasks.Task
import com.yugentech.shuki.repositories.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TasksViewModel(
    private val tasksRepository: TasksRepository = Graph.tasksRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task.asStateFlow()

    init {
        viewModelScope.launch {
            tasksRepository.initializeDummyData()
            tasksRepository.getAllTasks().collect { tasksList ->
                _tasks.value = tasksList
            }
        }
    }

    fun getTask(id: Long) {
        viewModelScope.launch {
            tasksRepository.getTask(id).collect { task ->
                _task.value = task
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.deleteTask(task)
        }
    }
}