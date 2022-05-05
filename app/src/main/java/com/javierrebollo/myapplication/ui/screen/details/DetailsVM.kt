package com.javierrebollo.myapplication.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierrebollo.myapplication.domain.entity.Room
import com.javierrebollo.myapplication.domain.entity.on
import com.javierrebollo.myapplication.domain.usecase.BookRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsVM @Inject constructor(
    private val bookRoomUseCase: BookRoomUseCase
) : ViewModel() {

    private val _toastMessage = Channel<String>(Channel.BUFFERED)
    val toastMessage: Flow<String> = _toastMessage.receiveAsFlow()

    fun bookRoom(room: Room) {
        viewModelScope.launch {
            bookRoomUseCase().on(
                success = {
                    _toastMessage.send("${room.name} Booked")
                },
                failure = {
                    _toastMessage.send(it.localizedMessage ?: "")
                }
            )
        }
    }
}
