package com.legion1900.game_list_feature.providers

import com.legion1900.base.exceptions.ConnectivityException
import com.legion1900.game_list_feature.models.GameCover
import com.legion1900.game_list_feature.models.GameDetails

class GamesRepository internal constructor(
    private val remoteGameSource: RemoteGameSource,
    private val localGameSource: GameStorage
) {

    suspend fun getGameCovers(
        offset: Int,
        limit: Int
    ): List<GameCover> {
        var gameDetails = localGameSource.getGameDetails(offset, limit)
        if (gameDetails.isEmpty()) {
            try {
                gameDetails = remoteGameSource.getGameDetails(offset, limit)
                localGameSource.storeGames(gameDetails)
            } catch (e: ConnectivityException) {
                gameDetails = emptyList()
            }
        }

        return gameDetails.toCover()
    }

    private fun List<GameDetails>.toCover(): List<GameCover> {
        return map { details ->
            val cover = details.screenshot.lastOrNull() ?: details.cover ?: details.artworks.firstOrNull()
            GameCover(details.id, cover, details.name)
        }
    }
}
