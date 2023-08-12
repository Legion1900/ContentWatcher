package com.legion1900.game_list_feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.legion1900.game_list_feature.databinding.GameCoverBinding
import com.legion1900.game_list_feature.models.GameCover

class GameCoversAdapter : Adapter<GameCoversAdapter.GameCoverVH>() {

    var covers: List<GameCover> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameCoverVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GameCoverBinding.inflate(inflater, parent, false)
        return GameCoverVH(binding)
    }

    override fun getItemCount(): Int = covers.size

    override fun onBindViewHolder(
        holder: GameCoverVH,
        position: Int
    ) {
        holder.bind(covers[position])
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
}
