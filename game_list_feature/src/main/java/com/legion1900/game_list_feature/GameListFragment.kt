package com.legion1900.game_list_feature

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.legion1900.base.WatcherFragment
import com.legion1900.game_list_feature.databinding.FragmentGameListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameListFragment : WatcherFragment(R.layout.fragment_game_list) {

    private val binding by viewBinding(FragmentGameListBinding::bind)
    private val viewModel by viewModel<GameListViewModel>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.getGamesBtn.setOnClickListener { viewModel.authOnTwitch() }
    }

    companion object {
        fun newInstance(): GameListFragment = GameListFragment()
    }
}
