package com.javierrebollo.myapplication.ui.screen.list

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.javierrebollo.myapplication.NavigationKeys.DETAILS
import com.javierrebollo.myapplication.domain.entity.Room
import com.javierrebollo.myapplication.ui.screen.list.component.ListOfRooms


@Composable
fun ListScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: ListVM = hiltViewModel(),
    navController: NavController
) {

    val roomList: State<List<Room>> = viewModel.roomItems.collectAsState(initial = emptyList())
    val context = LocalContext.current

    LaunchedEffect(lifecycleOwner) {
        viewModel.toastMessage
            .collect {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
    }

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.refresh()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (viewModel.showLoader.value) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )
    }

    ListOfRooms(roomList.value) {
        navController.navigate(DETAILS)
    }
}
