package com.legion1900.network.services.wrappers

internal object IGDBBodyTemplates {
    const val GAME_LIST_BODY = "fields name, summary, category, first_release_date, genres; offset %d; limit %d;"
    const val GAME_ART_BODY = "fields game, image_id; where game = %d;"
    const val GAME_SCREENSHOT_BODY = "fields game, image_id; where game = %d;"
    const val GAME_COVER_BODY = "fields image_id; where game = %d;"
}
