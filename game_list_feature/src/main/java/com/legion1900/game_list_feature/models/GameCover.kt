package com.legion1900.game_list_feature.models

data class GameCover(
    val id: GameId,
    val coverUrl: GameImage?,
    val title: String,
    val releaseDate: String
)
