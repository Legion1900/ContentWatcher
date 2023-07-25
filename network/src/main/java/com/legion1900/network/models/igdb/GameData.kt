package com.legion1900.network.models.igdb

import com.squareup.moshi.Json

data class GameData(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "summary") val summary: String = "",
    @Json(name = "category") val category: Int = UNDEFINED,
    @Json(name = "first_release_date") val releaseDate: Long = UNDEFINED_L,
    @Json(name = "genres") val genres: List<Int> = emptyList(),
) {
    companion object {
        const val UNDEFINED = -1
        const val UNDEFINED_L = -1L
    }
}
