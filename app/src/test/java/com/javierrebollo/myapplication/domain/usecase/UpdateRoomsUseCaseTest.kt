package com.javierrebollo.myapplication.domain.usecase

import com.javierrebollo.myapplication.data.repository.RoomRepository
import com.javierrebollo.myapplication.domain.entity.Room
import com.javierrebollo.myapplication.domain.entity.TaskResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class UpdateRoomsUseCaseTest {

    private val testDispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when we call invoke then should to call getAllRoomFromServer one time`() {
        val roomRepository: RoomRepository = mock()

        val updateRoomsUseCase = UpdateRoomsUseCase(
            dispatcher = testDispatcher,
            roomRepository = roomRepository
        )

        runTest {
            Mockito.`when`(roomRepository.getAllRoomFromServer()).thenReturn(
                TaskResult.ErrorResult(
                    Exception()
                )
            )

            updateRoomsUseCase.invoke()

            verify(roomRepository, times(1)).getAllRoomFromServer()
        }
    }

    @Test
    fun `when we call invoke and repository return success then should to call replaceRoomsInDB one time`() {
        val roomRepository: RoomRepository = mock()
        val response: List<Room> = emptyList()

        val updateRoomsUseCase = UpdateRoomsUseCase(
            dispatcher = testDispatcher,
            roomRepository = roomRepository
        )

        runTest {
            Mockito.`when`(roomRepository.getAllRoomFromServer()).thenReturn(
                TaskResult.SuccessResult(
                    response
                )
            )

            updateRoomsUseCase.invoke()

            verify(roomRepository, times(1)).replaceRoomsInDB(response)
        }
    }
}
