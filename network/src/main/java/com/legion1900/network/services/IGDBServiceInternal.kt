package com.legion1900.network.services

import com.legion1900.network.models.igdb.Artwork
import com.legion1900.network.models.igdb.Game
import retrofit2.http.Body
import retrofit2.http.POST

internal interface IGDBServiceInternal {

    @POST("v4/games")
    suspend fun games(@Body filter: String): List<Game>

    @POST("v4/artworks")
    suspend fun artworks(@Body filter: String): List<Artwork>
}
