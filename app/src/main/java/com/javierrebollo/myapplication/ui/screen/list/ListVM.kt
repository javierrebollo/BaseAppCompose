package com.javierrebollo.myapplication.ui.screen.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierrebollo.myapplication.data.repository.RoomRepository
import com.javierrebollo.myapplication.domain.entity.Room
import com.javierrebollo.myapplication.domain.entity.on
import com.javierrebollo.myapplication.domain.usecase.UpdateRoomsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListVM @Inject constructor(
    private val roomRepository: RoomRepository,
    private val updateRoomsUseCase: UpdateRoomsUseCase
) : ViewModel() {

    val roomItems: Flow<List<Room>> = roomRepository.getAllRoom()
    private val _showLoader: MutableState<Boolean> = mutableStateOf(false)
    val showLoader: State<Boolean> = _showLoader

    private val _toastMessage = Channel<String>(Channel.BUFFERED)
    val toastMessage: Flow<String> = _toastMessage.receiveAsFlow()

    fun refresh() {
        _showLoader.value = true

        viewModelScope.launch {
            updateRoomsUseCase().on(
                success = {
                    _showLoader.value = false
                },
                failure = {
                    _toastMessage.send(it.localizedMessage ?: "")
                    _showLoader.value = false
                }
            )
        }
    }
}
