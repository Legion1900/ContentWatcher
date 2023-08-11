package com.legion1900.database

import com.legion1900.database.objects.TwitchTokenObject
import com.legion1900.database.objects.games.GameObject
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val realmModule = module {

    factory {
        RealmConfiguration
            .Builder(
                setOf(
                    TwitchTokenObject::class,
                    GameObject::class
                )
            )
            .build()
    }

    single {
        val config = get<RealmConfiguration>()
        Realm.open(config)
    }
}
