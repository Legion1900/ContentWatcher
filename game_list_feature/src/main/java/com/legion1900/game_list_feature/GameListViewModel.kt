package com.legion1900.game_list_feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legion1900.network.services.TwitchService
import kotlinx.coroutines.launch

class GameListViewModel(
    private val twitchService: TwitchService
) : ViewModel() {

    fun authOnTwitch() {
        viewModelScope.launch {
            try {
                val result = twitchService.authenticateApp(
                    com.legion1900.network.BuildConfig.TWITCH_CLIENT_ID,
                    com.legion1900.network.BuildConfig.TWITCH_CLIENT_SECRET
                )

                Log.d("enigma", "Auth successful: ${result.accessToken}")
            } catch (e: Exception) {
                Log.e("enigma", "Error on auth", e)
            }
        }
    }
}
