package com.example.fashionmode.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fashionmode.Navigation.AppNavigation
import com.example.fashionmode.ui.theme.FashionModeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FashionModeTheme {
                AppNavigation()
            }
        }
    }
}