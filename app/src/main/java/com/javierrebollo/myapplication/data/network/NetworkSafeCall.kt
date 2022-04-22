package com.javierrebollo.myapplication.data.network

import com.javierrebollo.myapplication.data.network.exception.GenericNetworkException
import com.javierrebollo.myapplication.data.network.exception.NetworkException
import com.javierrebollo.myapplication.domain.entity.TaskResult
import retrofit2.Response

suspend fun <T, A> networkSafeCall(
    apiBlock: suspend () -> Response<T>,
    bodyCall: (response: T) -> TaskResult<A>
): TaskResult<A> {
    return try {
        val response = apiBlock.invoke()

        if (response.isSuccessful && response.body() != null) {
            bodyCall.invoke(response.body()!!)
        } else {
            TaskResult.ErrorResult(NetworkException(response.code()))
        }

    } catch (e: Exception) {
        TaskResult.ErrorResult(GenericNetworkException())
    }
}
