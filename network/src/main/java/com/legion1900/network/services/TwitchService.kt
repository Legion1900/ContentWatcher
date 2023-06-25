package com.legion1900.network.services

import com.legion1900.network.models.twitch.TwitchAuthResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface TwitchService {

    /**
     * @param grantType should be left with default value!
     */
    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun authenticateApp(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String = "client_credentials"
    ): TwitchAuthResponse
}
