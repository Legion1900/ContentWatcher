package com.legion1900.network.auth.realm

import com.legion1900.network.auth.AuthRepo
import com.legion1900.network.auth.TwitchAppToken

internal class RealmAuthRepo(private val twitchTokenDao: TwitchTokenDao) : AuthRepo {

    override suspend fun getTwitchAppToken(): TwitchAppToken? {
        return twitchTokenDao
            .getTwitchToken()
            ?.let { TwitchAppToken(it.token) }
    }

    override suspend fun storeTwitchAppToken(token: TwitchAppToken) {
        twitchTokenDao.putTwitchToken(token)
    }

    override suspend fun clearTwitchToken() {
        twitchTokenDao.clearToken()
    }
}
