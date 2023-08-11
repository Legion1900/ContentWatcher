package com.legion1900.game_list_feature.providers

import com.legion1900.game_list_feature.models.GameDetails

interface GameSource {
    suspend fun getGameDetails(
        offset: Int,
        limit: Int
    ): List<GameDetails>
}

interface GameStorage : GameSource {

    suspend fun storeGames(games: List<GameDetails>)
}
