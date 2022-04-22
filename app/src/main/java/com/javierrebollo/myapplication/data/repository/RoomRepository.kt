package com.javierrebollo.myapplication.data.repository

import com.javierrebollo.myapplication.data.db.dao.RoomDao
import com.javierrebollo.myapplication.data.db.entity.toRoom
import com.javierrebollo.myapplication.data.db.entity.toRoomDBO
import com.javierrebollo.myapplication.data.network.api.RoomApi
import com.javierrebollo.myapplication.data.network.networkSafeCall
import com.javierrebollo.myapplication.domain.di.IoDispatcher
import com.javierrebollo.myapplication.domain.entity.Room
import com.javierrebollo.myapplication.domain.entity.TaskResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomRepository @Inject constructor(
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val roomDao: RoomDao,
    private val roomApi: RoomApi
) {

    suspend fun getAllRoomFromServer(): TaskResult<List<Room>> {
        return withContext(coroutineDispatcher) {
            networkSafeCall({ roomApi.getRooms() }) { bookListResponse ->
                return@networkSafeCall TaskResult.SuccessResult(bookListResponse.rooms)
            }
        }
    }

    suspend fun bookRoom(): TaskResult<Unit> {
        return withContext(coroutineDispatcher) {
            networkSafeCall({ roomApi.bookRoom() }) { bookRoomResponse ->
                if (bookRoomResponse.success) {
                    return@networkSafeCall TaskResult.SuccessResult(Unit)
                } else {
                    return@networkSafeCall TaskResult.ErrorResult(Exception("Error when try to book a room"))
                }
            }
        }
    }

    suspend fun replaceRoomsInDB(roomList: List<Room>) {
        withContext(coroutineDispatcher) {
            roomDao.nukeTable()
            roomDao.insertAll(roomList.map { it.toRoomDBO() })
        }
    }

    fun getAllRoom(): Flow<List<Room>> = roomDao.getAll().map { list -> list.map { it.toRoom() } }
}
