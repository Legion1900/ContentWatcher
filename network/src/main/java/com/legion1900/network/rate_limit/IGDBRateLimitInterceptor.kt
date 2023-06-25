package com.legion1900.network.rate_limit

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

private const val REQUESTS_PER_SECOND = 4
private const val DELAY = 1_000L

@OptIn(DelicateCoroutinesApi::class)
internal class IGDBRateLimitInterceptor : Interceptor {

    private var ticker = Ticker()

    private val channel = Channel<Unit>(REQUESTS_PER_SECOND)

    init {
        GlobalScope.launch {
            ticker.ticks.collect {
                    for (i in 0 until REQUESTS_PER_SECOND) {
                        channel.tryReceive()
                    }
                }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        channel.send(Unit)
        val response = chain.run { proceed(request()) }
        response
    }
}

@OptIn(DelicateCoroutinesApi::class)
private class Ticker {

    private var isStarted = false

    private val _ticks = MutableSharedFlow<Unit>()

    val ticks: Flow<Unit>
        get() = _ticks.onSubscription { startIfNeeded() }

    private suspend fun startIfNeeded() {
        if (!isStarted) {
            isStarted = true

            GlobalScope.launch {
                while (true) {
                    delay(DELAY)
                    _ticks.emit(Unit)
                }
            }
        }
    }
}
