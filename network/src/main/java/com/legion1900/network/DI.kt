package com.legion1900.network

import com.legion1900.network.auth.AuthRepo
import com.legion1900.network.auth.IGDBAuthHandler
import com.legion1900.network.auth.IGDBAuthInterceptor
import com.legion1900.network.auth.SharedPrefsAuthRepo
import com.legion1900.network.converters.IGDBConverterFactory
import com.legion1900.network.services.IGDBService
import com.legion1900.network.services.TwitchService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.ParametersHolder
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single {
        val loggingInterceptor = get<HttpLoggingInterceptor>()
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {
        Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single { MoshiConverterFactory.create(get()) }

    factory { (client: OkHttpClient, baseUrl: String) ->
        Retrofit
            .Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(get<MoshiConverterFactory>())
            .build()
    }

    single {
        val twitchBaseUrl = "https://id.twitch.tv"
        val retrofit = get<Retrofit>(get<OkHttpClient>(), twitchBaseUrl)
        retrofit.create(TwitchService::class.java)
    }

    single {
        val igdbBaseUrl = "https://api.igdb.com/v4"
        val authInterceptor = get<IGDBAuthInterceptor>()
        val converter = get<IGDBConverterFactory>()
        val client = get<OkHttpClient>()
            .newBuilder()
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = get<Retrofit>(client, igdbBaseUrl)
            .newBuilder()
            .addConverterFactory(converter)
            .build()
        retrofit.create(IGDBService::class.java)
    }

    factory {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    single { IGDBAuthInterceptor(get()) }
    single { IGDBConverterFactory(get<MoshiConverterFactory>()) }

    single { IGDBAuthHandler(get(), get()) }
    single { SharedPrefsAuthRepo(get()) } bind AuthRepo::class
}

internal inline fun <reified T : Any> Scope.get(vararg params: Any): T {
    val values: MutableList<Any?> = params.toMutableList()
    return get(parameters = { ParametersHolder(values) })
}
