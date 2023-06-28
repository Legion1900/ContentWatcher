package com.legion1900.network.models.twitch

import com.squareup.moshi.Json

class TwitchAuthResponse(
    @Json(name = "access_token") val accessToken: String
)
