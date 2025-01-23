package com.yugentech.shuki.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yugentech.shuki.ui.components.ContactButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "About",
                        style = MaterialTheme.typography.headlineSmall,
                        color = colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.background,
                    titleContentColor = colorScheme.onBackground
                )
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(top = 16.dp),
                    tint = colorScheme.primary
                )

                Text(
                    text = "Shuki",
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = colorScheme.onBackground,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = "Shuki is a minimalistic task management app that helps you organize your daily activities and take notes efficiently. Simple, clean, and effective.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Made by Yugen Tech",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorScheme.onBackground,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = "Version 1.0.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                ContactButton(
                    icon = Icons.Default.Email,
                    label = "Contact Developer",
                    onClick = {
                        Toast.makeText(context, "Visit: https://linkedin.com", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                ContactButton(
                    icon = Icons.Default.Menu,
                    label = "Visit GitHub",
                    onClick = {
                        Toast.makeText(context, "Visit: https://github.com", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
            }
        }
    )
}