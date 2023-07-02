package com.legion1900.network.auth

import com.legion1900.network.BuildConfig
import com.legion1900.network.services.TwitchService

internal data class IGDBAuthInfo(
    val clientId: String,
    val authorization: String
)

internal class IGDBAuthHandler(
    private val twitchService: TwitchService,
    private val authRepo: AuthRepo
) {

    suspend fun getAuthDataOrAuthenticate(): IGDBAuthInfo {
        val twitchToken = getTwitchToken()
        return IGDBAuthInfo(
            BuildConfig.TWITCH_CLIENT_ID,
            "Bearer: ${twitchToken.token}"
        )
    }

    private suspend fun getTwitchToken(): TwitchAppToken {
        var twitchToken = authRepo.getTwitchAppToken()
        if (twitchToken == null) {
            val response = twitchService.authenticateApp(BuildConfig.TWITCH_CLIENT_ID, BuildConfig.TWITCH_CLIENT_SECRET)
            twitchToken = TwitchAppToken(response.accessToken)
            authRepo.storeTwitchAppToken(twitchToken)
        }

        return twitchToken
    }

    suspend fun invalidateToken() {
        authRepo.clearTwitchToken()
    }
}
