package com.example.fashionmode.ui.features.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fashionmode.Navigation.Routes.MAIN_WINDOW
import com.example.fashionmode.common.ui.FashionTextField
import com.example.fashionmode.ui.theme.ErrorColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignIn(signInViewModel: SignInViewModel = koinViewModel(), navController: NavController){
    val state by signInViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        signInViewModel.channel.collect { effect ->
            when (effect) {
                SignInEffect.NavigateToMain -> {
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
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
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

            Button(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                onClick = {
                    signInViewModel.handleIntent(SignInIntent.Login)
                }
            ) {
                Text("Войти")
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