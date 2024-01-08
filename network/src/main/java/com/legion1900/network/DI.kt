package com.legion1900.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.legion1900.network.auth.AuthRepo
import com.legion1900.network.auth.IGDBAuthHandler
import com.legion1900.network.auth.IGDBAuthInterceptor
import com.legion1900.network.auth.realm.RealmAuthRepo
import com.legion1900.network.auth.realm.TwitchTokenDao
import com.legion1900.network.converters.IGDBConverterFactory
import com.legion1900.network.error_handling.ErrorWrapperInterceptor
import com.legion1900.network.rate_limit.IGDBRateLimitInterceptor
import com.legion1900.network.services.IGDBServiceInternal
import com.legion1900.network.services.TwitchService
import com.legion1900.network.services.wrappers.IGDBService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.ParametersHolder
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single {
        val loggingInterceptor = get<HttpLoggingInterceptor>()
        val chuckerInterceptor = get<ChuckerInterceptor>()
        val errorWrapper = ErrorWrapperInterceptor()
        OkHttpClient
            .Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(errorWrapper)
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
            .build()
    }

    single {
        val twitchBaseUrl = "https://id.twitch.tv/"
        val retrofit = get<Retrofit>(get<OkHttpClient>(), twitchBaseUrl)
            .newBuilder()
            .addConverterFactory(get<MoshiConverterFactory>())
            .build()
        retrofit.create(TwitchService::class.java)
    }

    single {
        val igdbBaseUrl = "https://api.igdb.com/"
        val authInterceptor = get<IGDBAuthInterceptor>()
        val rateLimiter = get<IGDBRateLimitInterceptor>()
        val converter = get<IGDBConverterFactory>()
        val client = get<OkHttpClient>()
            .newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(rateLimiter)
            .build()
        get<Retrofit>(client, igdbBaseUrl)
            .newBuilder()
            .addConverterFactory(converter)
            .build()
            .create(IGDBServiceInternal::class.java)
    }

    factory {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    factory {
        val collector = ChuckerCollector(get(), retentionPeriod = RetentionManager.Period.ONE_DAY)
        ChuckerInterceptor.Builder(get())
            .collector(collector)
            .alwaysReadResponseBody(true)
            .createShortcut(true)
            .build()
    }

    single { IGDBAuthInterceptor(get()) }
    single { IGDBRateLimitInterceptor() }
    single { IGDBConverterFactory(get<MoshiConverterFactory>()) }

    single { IGDBAuthHandler(get(), get()) }
    singleOf(::RealmAuthRepo) bind AuthRepo::class

    singleOf(::IGDBService)

    singleOf(::TwitchTokenDao)
}

private inline fun <reified T : Any> Scope.get(vararg params: Any): T {
    val values: MutableList<Any?> = params.toMutableList()
    return get(parameters = { ParametersHolder(values) })
}
