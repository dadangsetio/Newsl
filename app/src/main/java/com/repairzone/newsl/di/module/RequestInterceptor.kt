package com.repairzone.newsl.di.module


import android.util.Log
import com.repairzone.newsl.BuildConfig
import com.repairzone.newsl.data.network.service.ApiServices.Factory.CUSTOM_HEADER
import com.repairzone.newsl.data.network.service.ApiServices.Factory.NO_AUTH
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject

class RequestInterceptor @Inject constructor() : Interceptor {
    private val mutex = Mutex()
    companion object{
        val TAG = "[Interceptor]"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (NO_AUTH in request.headers.values(CUSTOM_HEADER)){
            return chain.proceedWithToken(request, null)
        }

        val token = BuildConfig.API_KEY

        val response = chain.proceedWithToken(request, token)
        if (response.code != HTTP_UNAUTHORIZED) {
            return response
        }

        val newToken = runBlocking {
            mutex.withLock {

                when {
                    token != token -> token.also { Log.d(TAG, "$request") }
                    else -> {
                        null
                    }
                }
            }
        }

        return response
    }
}

private fun Interceptor.Chain.proceedWithToken(req: Request, token: String?): Response =
    req.newBuilder()
        .apply {
            Log.d(RequestInterceptor.TAG, "Network Error: $token")
            if (token !== null) {
                addHeader("Authorization", "$token")
            }
        }
        .removeHeader(CUSTOM_HEADER)
        .build()
        .also {
            if (BuildConfig.DEBUG) {
                Log.d(RequestInterceptor.TAG, it.method + " " + it.url)
                Log.d(RequestInterceptor.TAG, "" + it.headers("Cookie"))
                Log.d(RequestInterceptor.TAG, "headers: ${it.headers.size}")
                val buffer = Buffer()
                it.body?.writeTo(buffer)
                Log.d(RequestInterceptor.TAG, "Payload- " + buffer.readUtf8())
            }
        }
        .let(::proceed)