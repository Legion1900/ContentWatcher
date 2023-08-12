package com.legion1900.game_list_feature

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.legion1900.base.WatcherFragment
import com.legion1900.game_list_feature.databinding.FragmentGameListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameListFragment : WatcherFragment(R.layout.fragment_game_list) {

    private val binding by viewBinding(FragmentGameListBinding::bind)
    private val viewModel by viewModel<GameListViewModel>()

    private val adapter = GameCoversAdapter()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameList.adapter = adapter
        binding.gameList.layoutManager = LinearLayoutManager(requireContext())
        binding.gameList.setHasFixedSize(true)

        loadSomeGames()
    }

    private fun loadSomeGames() {
        viewModel.askForGameCovers().handleResult { list ->
            Log.d("enigma", "results in fragment!")
            adapter.covers = list
        }
    }

    companion object {
        fun newInstance(): GameListFragment = GameListFragment()
    }
}
