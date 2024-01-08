package com.legion1900.game_list_feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.legion1900.game_list_feature.databinding.GameCoverBinding
import com.legion1900.game_list_feature.models.GameCover
import kotlinx.coroutines.Dispatchers

class GameCoversAdapter : PagingDataAdapter<GameCover, GameCoversAdapter.GameCoverVH>(DIFF_CALLBACK, workerDispatcher = Dispatchers.IO) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameCoverVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GameCoverBinding.inflate(inflater, parent, false)
        return GameCoverVH(binding)
    }


    override fun onBindViewHolder(
        holder: GameCoverVH,
        position: Int
    ) {
        // We do not support placeholders at the moment.
        getItem(position)!!.let(holder::bind)
    }

    class GameCoverVH(private val binding: GameCoverBinding) : ViewHolder(binding.root) {

        fun bind(cover: GameCover) {
            binding.apply {
                title.text = cover.title
                Glide
                    .with(binding.root)
                    .load(cover.coverUrl?.url)
                    .into(binding.gameImage)
                releaseDate.text = releaseDate.context.getString(R.string.game_card_release_date, cover.releaseDate)
            }
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GameCover>() {
            override fun areItemsTheSame(
                oldItem: GameCover,
                newItem: GameCover
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GameCover,
                newItem: GameCover
            ): Boolean = oldItem == newItem
        }
    }
}
