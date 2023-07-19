package com.legion1900.network.models.igdb

import com.squareup.moshi.Json

data class Screenshot(
    @Json(name = "id") val id: Long,
    @Json(name = "image_id") val imageId: String
)
