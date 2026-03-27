package com.example.fashionmode.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fashionmode.Navigation.Routes.SIGN_UP
import com.example.fashionmode.ui.features.signup.SignUp
import com.example.fashionmode.ui.features.signup.SignUpViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = SIGN_UP
    ) {
        composable(SIGN_UP) {
            SignUp(navController = navController)
        }
        composable("main") {

        }
    }
}