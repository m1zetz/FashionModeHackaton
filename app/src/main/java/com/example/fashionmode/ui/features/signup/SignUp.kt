package com.example.fashionmode.ui.features.signup

import androidx.compose.foundation.layout.Arrangement.spacedBy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fashionmode.Navigation.Routes.MAIN_WINDOW
import com.example.fashionmode.common.ui.FashionTextField
import com.example.fashionmode.ui.theme.ErrorColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUp(signUpViewModel: SignUpViewModel = koinViewModel(), navController: NavController) {
    val state by signUpViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        signUpViewModel.channel.collect { effect ->
            when (effect) {
                SignUpEffect.NavigateBack -> {
                    navController.popBackStack()
                }
                SignUpEffect.NavigateToMain -> {
                    navController.navigate(MAIN_WINDOW)
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
                .padding(paddingValues).padding(8.dp),
            verticalArrangement = spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("AVISHU", fontSize = 24.sp)

            FashionTextField(
                value = state.name,
                onValueChange = { signUpViewModel.handleIntent(SignUpIntent.ChangeName(it)) },
                placeholder = "Введите никнейм",
                leadingIcon = Icons.Default.Person
            )
            FashionTextField(
                value = state.email,
                onValueChange = { signUpViewModel.handleIntent(SignUpIntent.ChangeEmail(it)) },
                placeholder = "Введите почту",
                leadingIcon = Icons.Default.Email
            )
            FashionTextField(
                value = state.password,
                onValueChange = { signUpViewModel.handleIntent(SignUpIntent.ChangePassword(it)) },
                placeholder = "Введите пароль",
                leadingIcon = Icons.Default.Lock
            )
            Button(
                onClick = {
                    signUpViewModel.handleIntent(SignUpIntent.Registration)
                }
            ){
                Text("Регистрация")
            }
            if(state.error.isNotEmpty()){
                Text(state.error, color = ErrorColor)
            }

        }
    }
}