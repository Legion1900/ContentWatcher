package com.legion1900.network.error_handling

import com.legion1900.base.exceptions.ConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.UnknownHostException

class ErrorWrapperInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            throw wrap(e)
        }
    }

    private fun wrap(e: Exception): Exception {
        return when (e) {
            is UnknownHostException -> ConnectivityException(e)
            else -> e
        }
    }
}
