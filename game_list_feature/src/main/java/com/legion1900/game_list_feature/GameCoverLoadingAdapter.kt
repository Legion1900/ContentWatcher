package com.legion1900.game_list_feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.legion1900.game_list_feature.databinding.ItemGameLoadingBinding

internal class GameCoverLoadingAdapter : LoadStateAdapter<GameCoverLoadingAdapter.LoadingVH>() {

    class LoadingVH(
        binding: ItemGameLoadingBinding,
        state: LoadState,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(state: LoadState) {

        }
    }

    override fun onBindViewHolder(
        holder: LoadingVH,
        loadState: LoadState,
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadingVH {
        val binding = ItemGameLoadingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return LoadingVH(binding, loadState)
    }
}
