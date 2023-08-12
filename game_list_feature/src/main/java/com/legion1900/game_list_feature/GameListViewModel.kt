package com.legion1900.game_list_feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.legion1900.base.utils.wrapResult
import com.legion1900.game_list_feature.models.GameCover
import com.legion1900.game_list_feature.providers.GamesRepository

class GameListViewModel(
    private val gamesRepository: GamesRepository
) : ViewModel() {

    fun askForGameCovers(): LiveData<Result<List<GameCover>>> {
        return wrapResult { gamesRepository.getGameCovers(0, 10) }
    }
}
