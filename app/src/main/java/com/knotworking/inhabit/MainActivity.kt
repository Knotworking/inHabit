package com.knotworking.inhabit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.knotworking.inhabit.home.HomeScreen
import com.knotworking.inhabit.ui.theme.InHabitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InHabitTheme {
                HomeScreen()
            }
        }
    }
}