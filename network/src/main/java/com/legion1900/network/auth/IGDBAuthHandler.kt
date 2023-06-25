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

    /**
     * @param forceAuth send **true** to force auth request. Otherwise tries to read cached token first.
     * If response fails with 401 then send **true** to update cached token with new one.
     */
    suspend fun getAuthDataOrAuthenticate(forceAuth: Boolean = false): IGDBAuthInfo {
        val readCache = !forceAuth
        val twitchToken = getTwitchToken(readCache)
        return IGDBAuthInfo(
            BuildConfig.TWITCH_CLIENT_ID,
            "Bearer ${twitchToken.token}"
        )
    }

    private suspend fun getTwitchToken(readCache: Boolean): TwitchAppToken {
        var twitchToken: TwitchAppToken? = null
        if (readCache) twitchToken = authRepo.getTwitchAppToken()
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
