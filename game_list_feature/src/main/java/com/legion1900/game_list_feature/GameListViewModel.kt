package com.legion1900.game_list_feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.legion1900.game_list_feature.models.GameCover
import com.legion1900.game_list_feature.paging.GameCoverSource

class GameListViewModel(
    private val coverSourceFactory: GameCoverSource.Factory
) : ViewModel() {

    val pagingData: LiveData<PagingData<GameCover>> by lazy {
        val pageSize = 10
        val config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = pageSize * 2,
            enablePlaceholders = false,
        )

        Pager(config) { coverSourceFactory.create() }.liveData
    }
}
