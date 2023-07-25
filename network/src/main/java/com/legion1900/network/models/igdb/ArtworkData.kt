package com.legion1900.network.models.igdb

import com.squareup.moshi.Json

data class ArtworkData(
    @Json(name = "id") val id: Int,
    @Json(name = "game") val gameId: Int,
    @Json(name = "image_id") val imageId: String,
)
