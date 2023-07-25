package com.legion1900.network.services.wrappers

import com.legion1900.network.models.igdb.ArtworkData
import com.legion1900.network.models.igdb.CoverData
import com.legion1900.network.models.igdb.GameData
import com.legion1900.network.models.igdb.ScreenshotData
import com.legion1900.network.services.IGDBServiceInternal
import java.util.Locale
import kotlin.math.min

class IGDBService internal constructor(
    private val igdbService: IGDBServiceInternal
) {

    suspend fun getPopularGames(offset: Int, limit: Int = MAX_LIMIT): List<GameData> {
        val checkedLimit = min(limit, MAX_LIMIT)
        val filter = IGDBBodyTemplates.GAME_LIST_BODY.f(offset, checkedLimit)
        return igdbService.games(filter)
    }

    suspend fun artworks(gameId: Long): List<ArtworkData> {
        val filter = IGDBBodyTemplates.GAME_ART_BODY.f(gameId)
        return igdbService.artworks(filter)
    }

    suspend fun screenshots(gameId: Long): List<ScreenshotData> {
        val filter = IGDBBodyTemplates.GAME_SCREENSHOT_BODY.f(gameId)
        return igdbService.screenshots(filter)
    }
    suspend fun covers(gameId: Long): List<CoverData> {
        val filter = IGDBBodyTemplates.GAME_COVER_BODY.f(gameId)
        return igdbService.covers(filter)
    }

    private fun String.f(vararg params: Any) = format(Locale.ENGLISH, *params)

    companion object {
        private const val MAX_LIMIT = 500
    }
}
