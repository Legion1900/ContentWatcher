package com.legion1900.network.auth

internal interface AuthRepo {
    suspend fun getTwitchAppToken(): TwitchAppToken?
    suspend fun storeTwitchAppToken(token: TwitchAppToken)
    suspend fun clearTwitchToken()
}

@JvmInline
value class TwitchAppToken(val token: String)
