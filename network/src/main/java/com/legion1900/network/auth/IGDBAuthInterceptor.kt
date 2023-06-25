package com.legion1900.network.auth

import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val IGDB_HEADER_CLIENT_ID = "Client-ID"
private const val IGDB_HEADER_AUTHORIZATION = "Authorization"

private const val AUTH_ERROR_CODE = 401
private const val TOO_MANY_REQUESTS_ERROR_CODE = 401

internal class IGDBAuthInterceptor(
    private val authHandler: IGDBAuthHandler,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("interceptorSpecial", "${Thread.currentThread().id}")
        return runBlocking {
            val request = buildAuthenticatedRequest(chain)
            runRequest(chain, request)
        }
    }

    private suspend fun buildAuthenticatedRequest(chain: Interceptor.Chain): Request {
        val (id, auth) = authHandler.getAuthDataOrAuthenticate()

        return chain
            .request()
            .newBuilder()
            .addHeader(IGDB_HEADER_CLIENT_ID, id)
            .addHeader(IGDB_HEADER_AUTHORIZATION, auth)
            .build()
    }

    private suspend fun runRequest(chain: Interceptor.Chain, request: Request): Response {
        val response = chain.proceed(request)
        return if (response.code == AUTH_ERROR_CODE) {
            authHandler.invalidateToken()
            response.close()
            intercept(chain)
        } else {
            response
        }
    }
}
