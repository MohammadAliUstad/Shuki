package com.yugentech.shuki.navigation

sealed class Screens(val route: String) {
    data object HomeScreen : Screens("home")
    data object AboutScreen : Screens("about")
    data object AddNoteScreen : Screens("addNote")
    data object AddTaskScreen : Screens("addTask")

    data object NoteEditScreen : Screens("noteEdit/{noteId}") {
        fun createRoute(noteId: String) = "noteEdit/$noteId"
    }

    data object TaskEditScreen : Screens("taskEdit/{taskId}") {
        fun createRoute(taskId: String) = "taskEdit/$taskId"
    }

    data object NoteDetailsScreen : Screens("noteDetails/{noteId}") {
        fun createRoute(noteId: String) = "noteDetails/$noteId"
    }

    data object TaskDetailsScreen : Screens("taskDetails/{taskId}") {
        fun createRoute(taskId: String) = "taskDetails/$taskId"
    }
}