package com.legion1900.database.objects.games

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class GameObject : RealmObject {
    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var summary: String = ""
    var cover: GameImageObject? = null
    var screenshots: RealmList<GameImageObject> = realmListOf()
    var artworks: RealmList<GameImageObject> = realmListOf()
    var category: String = ""
    var releaseDate: Long = 0
    var genres: RealmList<GameGenreObject> = realmListOf()
    var writeDate: Long = System.currentTimeMillis()
}

class GameImageObject : RealmObject {
    @PrimaryKey
    var id: String = ""
    var gameId: Long = 0
    var url: String = ""
}

class GameGenreObject : RealmObject {
    @PrimaryKey
    var id: Long = 0
    var name: String = ""
}
