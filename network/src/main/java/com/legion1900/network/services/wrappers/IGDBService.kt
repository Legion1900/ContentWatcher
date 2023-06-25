package com.legion1900.network.services.wrappers

import com.legion1900.network.models.igdb.Artwork
import com.legion1900.network.models.igdb.Game
import com.legion1900.network.services.IGDBServiceInternal
import java.util.Locale
import kotlin.math.min

class IGDBService internal constructor(
    private val igdbService: IGDBServiceInternal
) {

    suspend fun getPopularGames(offset: Int, limit: Int = MAX_LIMIT): List<Game> {
        val checkedLimit = min(limit, MAX_LIMIT)
        val filter = IGDBBodyTemplates.GAME_LIST_BODY.format(Locale.ENGLISH, offset, checkedLimit)
        return igdbService.games(filter)
    }

    suspend fun artworks(filter: String): List<Artwork> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val MAX_LIMIT = 500
    }
}
