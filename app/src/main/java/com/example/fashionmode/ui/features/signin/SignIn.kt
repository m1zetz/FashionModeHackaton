package com.example.fashionmode.ui.features.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fashionmode.Navigation.Routes.MAIN_WINDOW_CLIENT
import com.example.fashionmode.Navigation.Routes.MAIN_WINDOW_FRANCH
import com.example.fashionmode.Navigation.Routes.MAIN_WINDOW_MASTER
import com.example.fashionmode.Navigation.Routes.SIGN_UP
import com.example.fashionmode.common.enums.UserType
import com.example.fashionmode.common.ui.FashionTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignIn(signInViewModel: SignInViewModel = koinViewModel(), navController: NavController){
    val state by signInViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        signInViewModel.channel.collect { effect ->
            when (effect) {
                SignInEffect.NavigateToRegistration -> {
                    navController.navigate(SIGN_UP)
                }

                is SignInEffect.NavigateToMain -> {
                    when(effect.type){
                        UserType.CLIENT -> {
                            navController.navigate(MAIN_WINDOW_CLIENT)
                        }
                        UserType.FRANCH -> {
                            navController.navigate(MAIN_WINDOW_FRANCH)
                        }
                        UserType.MASTER -> {
                            navController.navigate(MAIN_WINDOW_MASTER)
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "AVISHU",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            FashionTextField(
                value = state.email,
                onValueChange = { signInViewModel.handleIntent(SignInIntent.ChangeEmail(it)) },
                placeholder = "Введите почту",
                leadingIcon = Icons.Default.Email
            )

            FashionTextField(
                value = state.password,
                onValueChange = { signInViewModel.handleIntent(SignInIntent.ChangePassword(it)) },
                placeholder = "Введите пароль",
                leadingIcon = Icons.Default.Lock,
                visualTransformation = PasswordVisualTransformation()
            )
            if(state.isLoading){
                CircularProgressIndicator()
            } else{
                Button(
                    onClick = {
                        signInViewModel.handleIntent(SignInIntent.Login)
                    }
                ) {
                    Text("Войти", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
                TextButton(
                    onClick = {
                        signInViewModel.handleIntent(SignInIntent.NavigateToRegistration)
                    }
                ) {
                    Text("Регистрация")
                }
            }

            if (state.error.isNotEmpty()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }


        }
    }
}