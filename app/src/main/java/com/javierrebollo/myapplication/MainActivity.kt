package com.javierrebollo.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.javierrebollo.myapplication.ui.screen.dummy.DummyScreen
import com.javierrebollo.myapplication.ui.theme.JavierRebolloTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JavierRebolloTestTheme {
                // Main app content comes here
                DummyScreen()
            }
        }
    }
}
