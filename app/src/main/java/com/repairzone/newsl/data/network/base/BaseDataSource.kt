package com.repairzone.newsl.data.network.base

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

abstract class BaseDataSource {
    protected suspend fun <T> apiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        val response: Response<T>
        try {
            response = apiCall()
        } catch (e: Exception) {
            return when (e) {
                is HttpException -> Resource.Error(mapHttpThrowable(e.code(), e.message()))
                is SocketTimeoutException -> Resource.Error(e)
                else -> Resource.Error(e)
            }
        }
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                body.let {
                    return Resource.Success(it)
                }
            }else{
                return Resource.Error(NullBodyException("Body cant be null"))
            }
        }else{
            return Resource.Error(mapHttpThrowable(response.code(), response.message()))
        }
    }

    private fun mapHttpThrowable(code: Int, message: String,): Throwable{
        val exception = when(code){
            400 -> BadRequestException(message)
            401 -> UnAuthenticationException(message)
            404 -> NotFoundException(message)
            402 -> PaymentRequiredException(message)
            500 -> ServerErrorException(message)
            502 -> BadGatewayException(message)
            else -> UnknownException(message)
        }
        return exception
    }
}