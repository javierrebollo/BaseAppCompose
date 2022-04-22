package com.javierrebollo.myapplication.data.repository

import com.javierrebollo.myapplication.data.network.api.RoomApi
import com.javierrebollo.myapplication.data.network.exception.GenericNetworkException
import com.javierrebollo.myapplication.data.network.exception.NetworkException
import com.javierrebollo.myapplication.data.network.response.BookListResponse
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.Response

@ExperimentalCoroutinesApi
class RoomRepositoryTest {

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
    fun `when call to getAllRoomFromServer without connection then should to return error`() {
        val roomApi: RoomApi = mock()

        runTest {
            Mockito.`when`(roomApi.getRooms()).thenThrow(RuntimeException())

            val roomRepository = RoomRepository(
                coroutineDispatcher = testDispatcher,
                roomDao = mock(),
                roomApi = roomApi
            )

            val result = roomRepository.getAllRoomFromServer()

            assert((result as TaskResult.ErrorResult).exception is GenericNetworkException)
        }
    }

    @Test
    fun `when call to getAllRoomFromServer with error response then should to return error`() {
        val errorCode = 404
        val roomApi: RoomApi = mock()
        val apiResponse: Response<BookListResponse> = mock {
            on { isSuccessful } doReturn false
            on { code() } doReturn errorCode
        }

        runTest {
            Mockito.`when`(roomApi.getRooms()).doReturn(apiResponse)

            val roomRepository = RoomRepository(
                coroutineDispatcher = testDispatcher,
                roomDao = mock(),
                roomApi = roomApi
            )

            val result = roomRepository.getAllRoomFromServer()

            assert((result as TaskResult.ErrorResult).exception is NetworkException)
            assert((result.exception as NetworkException).errorCode == errorCode)
        }
    }

    @Test
    fun `when call to getAllRoomFromServer success then should to return the whole list`() {
        val roomApi: RoomApi = mock()
        val expectedResult = BookListResponse(
            listOf(
                Room(
                    name = "name1",
                    spots = 1,
                    thumbnail = "thumbnail1",
                ),
                Room(
                    name = "name2",
                    spots = 2,
                    thumbnail = "thumbnail2",
                )
            )
        )
        val apiResponse: Response<BookListResponse> = mock {
            on { isSuccessful } doReturn true
            on { body() } doReturn expectedResult
        }

        runTest {
            Mockito.`when`(roomApi.getRooms()).doReturn(apiResponse)

            val roomRepository = RoomRepository(
                coroutineDispatcher = testDispatcher,
                roomDao = mock(),
                roomApi = roomApi
            )

            val result = roomRepository.getAllRoomFromServer()

            assertEquals(
                (result as TaskResult.SuccessResult<List<Room>>).value,
                expectedResult.rooms
            )
        }
    }
}
