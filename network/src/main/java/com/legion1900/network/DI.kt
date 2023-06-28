package com.legion1900.network

import com.legion1900.network.services.TwitchService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        val client = get<OkHttpClient>()
        val moshi = get<Moshi>()
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://id.twitch.tv")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        retrofit.create(TwitchService::class.java)
    }

    factory {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single {
        val loggingInterceptor = get<HttpLoggingInterceptor>()
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
}
