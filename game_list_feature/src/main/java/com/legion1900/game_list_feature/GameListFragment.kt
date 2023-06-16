package com.legion1900.game_list_feature

import by.kirich1409.viewbindingdelegate.viewBinding
import com.legion1900.base.WatcherFragment
import com.legion1900.game_list_feature.databinding.FragmentGameListBinding

class GameListFragment : WatcherFragment(R.layout.fragment_game_list) {
    private val binding by viewBinding(FragmentGameListBinding::bind)

    companion object {
        fun newInstance(): GameListFragment = GameListFragment()
    }
}
