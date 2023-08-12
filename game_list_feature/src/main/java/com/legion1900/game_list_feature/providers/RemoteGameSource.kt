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
        val screenshotJobs = resolveScreenshotsAsync(gameIds)

        val screenshots = screenshotJobs.await()

        mapToDetails(gameData, screenshots)
    }

    private fun mapToDetails(
        gameData: List<GameData>,
        screenshots: Map<GameId, List<GameImage>>,
    ): List<GameDetails> {
        return gameData.map { data ->
            val gameId = GameId(data.id)
            val screens = screenshots[gameId] ?: emptyList()

            GameDetails(
                gameId, data.name, data.summary, screens,"", data.releaseDate, emptyList()
            )
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
}
