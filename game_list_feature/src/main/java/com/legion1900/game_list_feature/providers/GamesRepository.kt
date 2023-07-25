package com.legion1900.game_list_feature.providers

import com.legion1900.game_list_feature.models.GameCover
import com.legion1900.network.services.wrappers.IGDBService

class GamesRepository internal constructor(
    private val remoteGameSource: RemoteGameSource,
) {
    suspend fun getGameCovers(offset: Int, limit: Int): List<GameCover> {
        return remoteGameSource.getGameDetails(offset, limit)
            .map { details ->
                val cover = details.screenshot.lastOrNull() ?: details.cover ?: details.artworks.firstOrNull()
                GameCover(details.id, cover, details.name)
            }
    }
}
