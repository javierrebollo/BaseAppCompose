package com.javierrebollo.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.javierrebollo.myapplication.NavigationKeys.DETAILS
import com.javierrebollo.myapplication.NavigationKeys.LIST
import com.javierrebollo.myapplication.ui.screen.details.DetailsScreen
import com.javierrebollo.myapplication.ui.screen.list.ListScreen
import com.javierrebollo.myapplication.ui.theme.JavierRebolloTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            JavierRebolloTestTheme {
                NavHost(navController = navController, startDestination = LIST) {
                    composable(LIST) { ListScreen(navController = navController) }
                    composable(DETAILS) { DetailsScreen() }
                }
            }
        }
    }
}

object NavigationKeys {
    const val LIST: String = "list"
    const val DETAILS: String = "details"
}
