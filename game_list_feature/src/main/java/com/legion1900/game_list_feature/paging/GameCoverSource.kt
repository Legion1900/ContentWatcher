package com.legion1900.game_list_feature.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.legion1900.game_list_feature.models.GameCover
import com.legion1900.game_list_feature.providers.GamesRepository

class GameCoverSource(private val gamesRepository: GamesRepository) : PagingSource<Int, GameCover>() {

    override fun getRefreshKey(state: PagingState<Int, GameCover>): Int = DEFAULT_INIT_KEY

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameCover> {
        val offset = params.key ?: DEFAULT_INIT_KEY
        val limit = params.loadSize
        return try {
            val covers = gamesRepository.getGameCovers(offset, limit)
            val prevKey = if (offset == DEFAULT_INIT_KEY) null else offset
            val nextKey = if (covers.isNotEmpty()) offset + limit else null
            LoadResult.Page(covers, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    class Factory(private val gamesRepository: GamesRepository) {

        fun create(): GameCoverSource = GameCoverSource(gamesRepository)
    }

    companion object {

        private const val DEFAULT_INIT_KEY = 0
    }
}
