package com.legion1900.game_list_feature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legion1900.base.utils.wrapResult
import com.legion1900.game_list_feature.models.GameCover
import com.legion1900.game_list_feature.providers.GamesRepository
import kotlinx.coroutines.launch

class GameListViewModel(
    private val gamesRepository: GamesRepository
) : ViewModel() {

    fun askForGameCovers(): LiveData<Result<List<GameCover>>> {
        return wrapResult { gamesRepository.getGameCovers(0, 10).apply { Log.d("enigma", "results in vm; $size of games") } }
    }
}
