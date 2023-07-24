package com.legion1900.network.auth.realm

import com.legion1900.database.objects.TwitchTokenObject
import com.legion1900.network.auth.TwitchAppToken
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.first

internal class TwitchTokenDao(private val realm: Realm) {

    suspend fun getTwitchToken(): TwitchTokenObject? {
        return realm
            .query<TwitchTokenObject>()
            .first()
            .asFlow()
            .first()
            .obj
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
