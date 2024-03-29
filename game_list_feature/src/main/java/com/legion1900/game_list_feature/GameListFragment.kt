package com.legion1900.game_list_feature

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.legion1900.base.WatcherFragment
import com.legion1900.base.utils.observe
import com.legion1900.base.views.rv.ItemSpacing
import com.legion1900.game_list_feature.databinding.FragmentGameListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameListFragment : WatcherFragment(R.layout.fragment_game_list) {

    private val binding by viewBinding(FragmentGameListBinding::bind)
    private val viewModel by viewModel<GameListViewModel>()

    private val adapter = GameCoversAdapter()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupGameList()
        initGamePaging()
    }

    private fun setupGameList() {
        val spacingDecoration = ItemSpacing.build(requireContext()) {
            leftMargin = R.dimen.game_cover_card_margin_horizontal
            rightMargin = R.dimen.game_cover_card_margin_horizontal
            topMargin = R.dimen.page_margin_padding_vertical
            botMargin = R.dimen.page_margin_padding_vertical
            verticalItemSpacing = R.dimen.game_cover_card_margin_vertical
        }

        binding.gameList.apply {
            adapter = this@GameListFragment.adapter.withLoadStateHeaderAndFooter(
                header = GameCoverLoadingAdapter(),
                footer = GameCoverLoadingAdapter(),
            )
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(spacingDecoration)
        }
    }

    private fun initGamePaging() {
        observe(viewModel.pagingData) { adapter.submitData(viewLifecycleOwner.lifecycle, it) }
    }

    companion object {

        fun newInstance(): GameListFragment = GameListFragment()
    }
}
