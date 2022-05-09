package com.javierrebollo.myapplication.domain.usecase

import com.javierrebollo.core.coroutine.MyCoroutineDispatcher
import com.javierrebollo.myapplication.data.repository.RoomRepository
import com.javierrebollo.myapplication.domain.entity.TaskResult
import com.javierrebollo.myapplication.domain.entity.on
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateRoomsUseCase @Inject constructor(
    private val dispatcher: MyCoroutineDispatcher,
    private val roomRepository: RoomRepository
) {

    suspend operator fun invoke(): TaskResult<Unit> {
        return withContext(dispatcher.default) {
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
