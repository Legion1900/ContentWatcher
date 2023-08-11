package com.legion1900.game_list_feature.providers

import com.legion1900.game_list_feature.models.GameDetails
import com.legion1900.game_list_feature.models.GameId
import com.legion1900.game_list_feature.models.GameImage
import com.legion1900.network.models.igdb.GameData
import com.legion1900.network.services.wrappers.IGDBService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal class RemoteGameSource(
    private val igdbService: IGDBService,
    private val igdbUrlFactory: IGDBUrlFactory,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GameSource {

    override suspend fun getGameDetails(
        offset: Int,
        limit: Int
    ): List<GameDetails> = coroutineScope {
        val gameData = igdbService.getPopularGames(offset, limit)
        val gameIds = gameData.map { GameId(it.id) }
        val artworkJobs = resolveArtworksAsync(gameIds)
        val screenshotJobs = resolveScreenshotsAsync(gameIds)
        val coverJobs = resolveCoverAsync(gameIds)

        val artworks = artworkJobs.await()
        val screenshots = screenshotJobs.await()
        val covers = coverJobs.await()

        mapToDetails(gameData, artworks, screenshots, covers)
    }

    private fun mapToDetails(
        gameData: List<GameData>,
        artworks: Map<GameId, List<GameImage>>,
        screenshots: Map<GameId, List<GameImage>>,
        covers: Map<GameId, GameImage>
    ): List<GameDetails> {
        return gameData.map { data ->
            val gameId = GameId(data.id)
            val gameArt = artworks[gameId] ?: emptyList()
            val screens = screenshots[gameId] ?: emptyList()
            val cover = covers[gameId]

            GameDetails(
                gameId, data.name, data.summary, cover, screens, gameArt, "", data.releaseDate, emptyList()
            )
        }
    }

    private fun CoroutineScope.resolveArtworksAsync(games: List<GameId>): Deferred<Map<GameId, List<GameImage>>> {
        return async(dispatcher) {
            games
                .map { gameId ->
                    async(dispatcher) {
                        igdbService
                            .artworks(gameId.value)
                            .map { artData ->
                                val url = igdbUrlFactory.newArtworkUrl(artData.imageId)
                                GameImage(artData.imageId, gameId, url)
                            }
                    }
                }
                .awaitAll()
                .flatten()
                .groupBy { it.gameId }
        }
    }

    private fun CoroutineScope.resolveScreenshotsAsync(games: List<GameId>): Deferred<Map<GameId, List<GameImage>>> {
        return async(dispatcher) {
            games
                .map { gameId ->
                    async(dispatcher) {
                        igdbService
                            .screenshots(gameId.value)
                            .map { screenshotData ->
                                val url = igdbUrlFactory.newScreenshotUrl(screenshotData.imageId)
                                GameImage(screenshotData.imageId, gameId, url)
                            }
                    }
                }
                .awaitAll()
                .flatten()
                .groupBy { it.gameId }
        }
    }

    private fun CoroutineScope.resolveCoverAsync(games: List<GameId>): Deferred<Map<GameId, GameImage>> {
        return async(dispatcher) {
            val pairs = games
                .mapNotNull { gameId ->
                    igdbService
                        .covers(gameId.value)
                        .firstOrNull()
                        ?.let {
                            val url = igdbUrlFactory.newCoverUrl(it.imageId)
                            GameImage(it.imageId, gameId, url)
                        }
                }
                .map { it.gameId to it }
                .toTypedArray()

            mapOf(*pairs)
        }
    }
}
