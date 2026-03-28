package com.example.fashionmode.Navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fashionmode.Navigation.Routes.MAIN_WINDOW_CLIENT
import com.example.fashionmode.Navigation.Routes.MAIN_WINDOW_MASTER
import com.example.fashionmode.Navigation.Routes.SIGN_IN
import com.example.fashionmode.Navigation.Routes.SIGN_UP
import com.example.fashionmode.ui.features.client.Main.MainSwitcherClient
import com.example.fashionmode.ui.features.client.ribbon.preview.PreviewViewModel
import com.example.fashionmode.ui.features.client.ribbon.preview.ProductPreview
import com.example.fashionmode.ui.features.signin.SignIn
import com.example.fashionmode.ui.features.signup.SignUp
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    Surface {
        NavHost(
            navController = navController,
            startDestination = SIGN_IN
        ) {
            composable(SIGN_UP) {
                SignUp(navController = navController)
            }
            composable(SIGN_IN) {
                SignIn(navController = navController)
            }
            composable(MAIN_WINDOW_CLIENT) {
                MainSwitcherClient(navController = navController)
            }
            composable(MAIN_WINDOW_MASTER){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("оляляя")
                }
            }
            composable("${Routes.DETAILS}/{productId}"){
                    backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                val previewViewModel: PreviewViewModel = koinViewModel {
                    parametersOf(productId)
                }
                ProductPreview(viewModel = previewViewModel, navController)
            }
        }
    }

}