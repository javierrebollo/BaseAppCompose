package com.javierrebollo.myapplication.ui.screen.details

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner

@Composable
fun DetailsScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: DetailsVM = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(lifecycleOwner){
        viewModel.toastMessage
            .collect {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
    }
}
