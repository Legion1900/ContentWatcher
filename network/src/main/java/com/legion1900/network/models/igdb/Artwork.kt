package com.legion1900.network.models.igdb

import com.squareup.moshi.Json

class Artwork(
    @Json(name = "id") val id: Int,
    @Json(name = "game") val gameId: Int,
    @Json(name = "url") val url: String,
)
