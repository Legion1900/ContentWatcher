package com.legion1900.network.services.wrappers

internal object IGDBBodyTemplates {
    const val GAME_LIST_BODY = "fields name, summary, category, screenshots, cover, first_release_date, genres; offset %d; limit %d;"
}
