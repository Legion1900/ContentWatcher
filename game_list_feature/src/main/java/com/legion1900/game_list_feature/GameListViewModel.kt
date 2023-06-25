package com.legion1900.game_list_feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legion1900.network.services.wrappers.IGDBService
import kotlinx.coroutines.launch

class GameListViewModel(
    private val igdbService: IGDBService
) : ViewModel() {

    fun authOnTwitch() {
        viewModelScope.launch {
            try {
                val games = igdbService.getPopularGames(0)
                Log.d("enigma", "Got data; ${games.size} entries")
            } catch (e: Exception) {
                Log.e("enigma", "Error while querying list of games", e)
            }
        }
    }
}
