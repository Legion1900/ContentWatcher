package com.legion1900.network.models.igdb

import com.squareup.moshi.Json

class Game(
    @Json(name = "name") val name: String,
    @Json(name = "summary") val summary: String,
    @Json(name = "screenshots") val screenshots: List<Int>,
    @Json(name = "category") val category: Int,
    @Json(name = "cover") val cover: Int,
    @Json(name = "first_release_date") val releaseDate: Long,
    @Json(name = "genres") val genres: List<Int>,
)
