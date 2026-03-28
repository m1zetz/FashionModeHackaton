package com.example.fashionmode.ui.features.client.Main

import Ribbon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fashionmode.ui.features.client.basket.Basket
import com.example.fashionmode.ui.features.client.orders.Orders

import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSwitcherClient(clientViewModel: MainViewModelClient = koinViewModel(), navController: NavController) {
    val selectedItemIndex = clientViewModel.screenIndexState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AVISHU") },
                navigationIcon = {

                },
                actions = {

                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        },
        bottomBar = {
            BottomNavigation(
                selectedItemIndex = selectedItemIndex.value.selectedIndex,
                changeScreen = { index -> clientViewModel.handleIntent(MainIntentClient.ChangeScreen(index)) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when(selectedItemIndex.value.selectedIndex){
                0 -> Ribbon(navController = navController)
                1 -> Basket()
                2 -> Orders()
            }

        }

    }
}