package com.yugentech.shuki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.yugentech.shuki.navigation.AppNavHost
import com.yugentech.shuki.ui.theme.ShukiTheme
import com.yugentech.shuki.viewmodels.HomeViewModel
import com.yugentech.shuki.viewmodels.NotesViewModel
import com.yugentech.shuki.viewmodels.TasksViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            ShukiTheme {
                val navController = rememberNavController()
                AppNavHost(
                    homeViewModel = HomeViewModel(),
                    notesViewModel = NotesViewModel(),
                    tasksViewModel = TasksViewModel(),
                    navController = navController
                )
            }
        }
    }
}