package com.legion1900.game_list_feature.providers

import com.legion1900.network.services.wrappers.IGDBService

data class Game(
    val id: Int,
    val coverUrl: String,
    val title: String,
)

class GamesRepository(
    private val igdbService: IGDBService
) {
    suspend fun getGames(offset: Int, limit: Int): List<Game> {
        TODO()
    }
}
