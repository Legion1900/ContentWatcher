package com.legion1900.game_list_feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legion1900.game_list_feature.providers.GamesRepository
import com.legion1900.network.services.wrappers.IGDBService
import kotlinx.coroutines.launch
import org.koin.core.time.measureDuration
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.seconds

class GameListViewModel(
    private val gamesRepository: GamesRepository
) : ViewModel() {

    fun authOnTwitch() {
        viewModelScope.launch {
            val time = measureTimeMillis {
                try {
                    gamesRepository.getGameCovers(0, 10)
                        .forEach {
                            Log.d("enigma", it.toString())
                        }
                } catch (e: Exception) {
                    Log.e("enigma", "Error while querying list of games", e)
                }
            }

            Log.d("enigma", "Total duration: ${time / 1_000f}")
        }
    }
}
