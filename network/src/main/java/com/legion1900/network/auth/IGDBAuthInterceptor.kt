package com.legion1900.network.auth

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

private const val IGDB_HEADER_CLIENT_ID = "Client-ID"
private const val IGDB_HEADER_AUTHORIZATION = "Authorization"

internal class IGDBAuthInterceptor(
    private val authHandler: IGDBAuthHandler
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val (id, auth) = runBlocking {
            authHandler.getAuthDataOrAuthenticate()
        }

        val request = chain.request()
            .newBuilder()
            .addHeader(IGDB_HEADER_CLIENT_ID, id)
            .addHeader(IGDB_HEADER_AUTHORIZATION, auth)
            .build()

        return chain.proceed(request)
    }
}
