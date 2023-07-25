package com.legion1900.network.services

import com.legion1900.network.models.igdb.ArtworkData
import com.legion1900.network.models.igdb.CoverData
import com.legion1900.network.models.igdb.GameData
import com.legion1900.network.models.igdb.ScreenshotData
import retrofit2.http.Body
import retrofit2.http.POST

internal interface IGDBServiceInternal {

    @POST("v4/games")
    suspend fun games(@Body filter: String): List<GameData>

    @POST("v4/artworks")
    suspend fun artworks(@Body filter: String): List<ArtworkData>

    @POST("v4/screenshots")
    suspend fun screenshots(@Body filter: String): List<ScreenshotData>

    /**
     * Always return a single object
     */
    @POST("v4/covers")
    suspend fun covers(@Body filter: String): List<CoverData>
}
