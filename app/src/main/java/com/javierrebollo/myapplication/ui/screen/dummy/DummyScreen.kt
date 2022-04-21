package com.javierrebollo.myapplication.ui.screen.dummy

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.javierrebollo.myapplication.domain.entity.Room
import com.javierrebollo.myapplication.ui.screen.dummy.component.ListOfRooms


@Composable
fun DummyScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: DummyVM = hiltViewModel()
) {

    val roomList: State<List<Room>> = viewModel.roomItems.collectAsState(initial = emptyList())
    val context = LocalContext.current

    LaunchedEffect(lifecycleOwner){
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
        viewModel.bookRoom(
            it
        )
    }
}
