package com.legion1900.network.models.igdb

import com.squareup.moshi.Json

data class Game(
    @Json(name = "name") val name: String,
    @Json(name = "summary") val summary: String = "",
    @Json(name = "screenshots") val screenshots: List<Int> = emptyList(),
    @Json(name = "category") val category: Int = UNDEFINED,
    @Json(name = "cover") val cover: Int = UNDEFINED,
    @Json(name = "first_release_date") val releaseDate: Long = UNDEFINED_L,
    @Json(name = "genres") val genres: List<Int> = emptyList(),
) {
    companion object {
        const val UNDEFINED = -1
        const val UNDEFINED_L = -1L
    }
}
