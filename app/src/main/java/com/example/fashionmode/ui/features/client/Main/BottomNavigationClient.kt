package com.example.fashionmode.ui.features.client.Main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val unselected: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(selectedItemIndex: Int, changeScreen: (Int) -> Unit) {

    val items = listOf(
        BottomNavItem(
            title = "cloth",
            icon = Icons.Filled.Home,
            unselected = Icons.Outlined.Home,
        ),
        BottomNavItem(
            title = "basket",
            icon = Icons.Filled.ShoppingCart,
            unselected = Icons.Outlined.ShoppingCart,
        ),
        BottomNavItem(
            title = "orders",
            icon = Icons.Default.List,
            unselected = Icons.Outlined.List
        )
    )

    NavigationBar {

        items.forEachIndexed { index, item ->

            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    changeScreen(index)
                },
                label = {
                    Text(item.title)
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.icon
                        } else {
                            item.unselected
                        }, contentDescription = item.title
                    )
                }
            )
        }
    }

}