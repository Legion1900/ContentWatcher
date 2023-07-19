package com.legion1900.network.auth.realm

import com.legion1900.database.objects.TwitchTokenObject
import com.legion1900.network.auth.TwitchAppToken
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class TwitchTokenDao(
    private val realm: Realm,
    private val io: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getTwitchToken(): TwitchTokenObject? {
        return withContext(io) {
            realm
                .query<TwitchTokenObject>()
                .first()
                .find()
        }
    }

    suspend fun putTwitchToken(token: TwitchAppToken) {
        realm.write {
            TwitchTokenObject().token = token.token
        }
    }

    suspend fun clearToken() {
        realm.write {
            val token = query<TwitchTokenObject>().first().find()
            token?.let(::delete)
        }
    }
}
