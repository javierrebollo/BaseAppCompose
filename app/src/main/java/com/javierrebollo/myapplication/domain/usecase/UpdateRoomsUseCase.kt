package com.javierrebollo.myapplication.domain.usecase

import com.javierrebollo.myapplication.data.repository.RoomRepository
import com.javierrebollo.myapplication.domain.di.DefaultDispatcher
import com.javierrebollo.myapplication.domain.entity.TaskResult
import com.javierrebollo.myapplication.domain.entity.on
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateRoomsUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val roomRepository: RoomRepository
) {

    suspend operator fun invoke(): TaskResult<Unit> {
        return withContext(dispatcher) {
            roomRepository.getAllRoomFromServer()
                .on(
                    success = {
                        roomRepository.replaceRoomsInDB(it)
                        return@withContext TaskResult.SuccessResult(Unit)
                    },
                    failure = {
                        return@withContext TaskResult.ErrorResult(
                            Exception("Error loading new rooms")
                        )
                    }
                )
        }
    }
}
