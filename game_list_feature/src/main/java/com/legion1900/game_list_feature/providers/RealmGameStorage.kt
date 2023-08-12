package com.legion1900.game_list_feature.providers

import com.legion1900.database.objects.games.GameGenreObject
import com.legion1900.database.objects.games.GameImageObject
import com.legion1900.database.objects.games.GameObject
import com.legion1900.game_list_feature.models.GameDetails
import com.legion1900.game_list_feature.models.GameGenre
import com.legion1900.game_list_feature.models.GameId
import com.legion1900.game_list_feature.models.GameImage
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.UpdatedResults
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

internal class RealmGameStorage(private val realm: Realm) : GameStorage {

    override suspend fun storeGames(games: List<GameDetails>) {
        val gameObjects = games.map(::gameToObject)
        realm.write {
            gameObjects.map { copyToRealm(it, UpdatePolicy.ALL) }
        }
    }

    private fun gameToObject(game: GameDetails): GameObject {
        return GameObject().apply {
            id = game.id.value
            name = game.name
            summary = game.summary
            screenshots = game.screenshot.map(::imageToObject).toRealmList()
            category = game.category
            releaseDate = game.releaseDate
            genres = game.genres.map(::genreToObject).toRealmList()
        }
    }

    private fun imageToObject(img: GameImage): GameImageObject {
        return GameImageObject().apply {
                id = img.id
                gameId = img.gameId.value
                url = img.url
            }
    }

    private fun genreToObject(genre: GameGenre): GameGenreObject {
        return GameGenreObject().apply {
            id = genre.id
            name = genre.name
        }
    }

    override suspend fun getGameDetails(
        offset: Int,
        limit: Int
    ): List<GameDetails> {
        return realm
            .query<GameObject>()
            .sort("writeDate", Sort.ASCENDING)
            .asFlow()
            .drop(offset)
            .take(limit)
            .map { results ->
                when (results) {
                    is InitialResults -> results.list.map(::objectToDetails)
                    is UpdatedResults -> emptyList()
                }
            }
            .first()
    }

    private fun objectToDetails(obj: GameObject): GameDetails {
        return GameDetails(
            id = GameId(obj.id),
            name = obj.name,
            summary = obj.summary,
            screenshot = obj.screenshots.map(::imgObjectToImage),
            category = obj.category,
            releaseDate = obj.releaseDate,
            genres = obj.genres.map(::genreObjToGenre)
        )
    }

    private fun imgObjectToImage(obj: GameImageObject): GameImage {
        return GameImage(
            id = obj.id, gameId = GameId(obj.gameId), // Assuming GameId is a custom data class representing the gameId
            url = obj.url
        )
    }

    private fun genreObjToGenre(obj: GameGenreObject): GameGenre {
        return GameGenre(
            id = obj.id, name = obj.name
        )
    }
}
