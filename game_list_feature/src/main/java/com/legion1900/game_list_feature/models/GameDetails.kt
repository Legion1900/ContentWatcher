package com.legion1900.game_list_feature.models

data class GameDetails(
    val id: GameId,
    val name: String,
    val summary: String,
    val cover: GameImage?,
    val screenshot: List<GameImage>,
    val artworks: List<GameImage>,
    val category: String,
    val releaseDate: Long,
    val genres: List<GameGenre>,
)

@JvmInline
value class GameId(val value: Long)

data class GameImage(
    val id: String,
    val gameId: GameId,
    val url: String
)

data class GameGenre(
    val id: Long,
    val name: String
)
