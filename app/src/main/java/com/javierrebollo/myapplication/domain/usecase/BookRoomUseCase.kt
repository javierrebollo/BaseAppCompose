package com.javierrebollo.myapplication.domain.usecase

import com.javierrebollo.myapplication.data.repository.RoomRepository
import com.javierrebollo.myapplication.domain.di.DefaultDispatcher
import com.javierrebollo.myapplication.domain.entity.TaskResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookRoomUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val roomRepository: RoomRepository
) {

    suspend operator fun invoke(): TaskResult<Unit> {
        return withContext(dispatcher) {
            roomRepository.bookRoom()
        }
    }
}
