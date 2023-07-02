package com.legion1900.network.auth

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

private const val AUTH_PREFS = "auth_data"
private const val TWITCH_APP_TOKEN = "twitch_token"

internal class SharedPrefsAuthRepo(
    ctx: Context
) : AuthRepo {

    private val sharedPreferences by lazy { ctx.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE) }

    private val dedicatedThreadDispatcher = newSingleThreadContext("auth_repo_thread")
    private var cachedTwitchToken: TwitchAppToken? = null

    override suspend fun getTwitchAppToken(): TwitchAppToken? {
        return withContext(dedicatedThreadDispatcher) {
            if (cachedTwitchToken == null) {
                cachedTwitchToken = sharedPreferences
                    .getString(TWITCH_APP_TOKEN, null)
                    ?.let(::TwitchAppToken)
            }
            cachedTwitchToken
        }
    }

    override suspend fun storeTwitchAppToken(token: TwitchAppToken) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit()
                .putString(TWITCH_APP_TOKEN, token.token)
                .commit()
        }
    }

    override suspend fun clearTwitchToken() {
        withContext(dedicatedThreadDispatcher) {
            cachedTwitchToken = null
            sharedPreferences.edit()
                .remove(TWITCH_APP_TOKEN)
                .commit()
        }
    }
}
