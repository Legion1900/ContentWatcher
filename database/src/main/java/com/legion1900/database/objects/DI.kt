package com.legion1900.database.objects

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val realmModule = module {

    factory {
        RealmConfiguration
            .Builder(
                setOf(
                    TwitchTokenObject::class
                )
            )
            .build()
    }

    single {
        val config = get<RealmConfiguration>()
        Realm.open(config)
    }
}
