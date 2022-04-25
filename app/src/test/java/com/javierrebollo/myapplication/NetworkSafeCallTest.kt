package com.javierrebollo.myapplication

import com.javierrebollo.myapplication.data.network.exception.GenericNetworkException
import com.javierrebollo.myapplication.data.network.exception.NetworkException
import com.javierrebollo.myapplication.data.network.networkSafeCall
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
class NetworkSafeCallTest {

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
    fun `when call to networkSafeCall with bodyRequest with exception then should to return error`() {
        val bodyRequest: suspend () -> Response<Any> = mock()

        runTest {
            Mockito.`when`(bodyRequest.invoke()).thenThrow(RuntimeException())

            val result = networkSafeCall(bodyRequest) {
                return@networkSafeCall TaskResult.SuccessResult(Any())
            }


            assert((result as TaskResult.ErrorResult).exception is GenericNetworkException)
        }
    }

    @Test
    fun `when call to networkSafeCall with error response then should to return error`() {
        val errorCode = 404
        val apiResponse: Response<Any> = mock {
            on { isSuccessful } doReturn false
            on { code() } doReturn errorCode
        }

        runTest {
            val bodyRequest: suspend () -> Response<Any> = { apiResponse }

            val result = networkSafeCall(bodyRequest) {
                return@networkSafeCall TaskResult.SuccessResult(Any())
            }

            assert((result as TaskResult.ErrorResult).exception is NetworkException)
            assert((result.exception as NetworkException).errorCode == errorCode)
        }
    }

    @Test
    fun `when call to networkSafeCall success then should to return the expected result`() {
        val expectedResult = "ExpectedResult"

        val apiResponse: Response<Any> = mock {
            on { isSuccessful } doReturn true
            on { body() } doReturn expectedResult
        }

        runTest {
            val bodyRequest: suspend () -> Response<Any> = { apiResponse }

            val result = networkSafeCall(bodyRequest) {
                return@networkSafeCall TaskResult.SuccessResult(expectedResult)
            }

            assertEquals(
                (result as TaskResult.SuccessResult<String>).value,
                expectedResult
            )
        }
    }
}
