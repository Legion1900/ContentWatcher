package com.legion1900.database.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class TwitchTokenObject : RealmObject {
    @PrimaryKey
    var token: String = ""
}
