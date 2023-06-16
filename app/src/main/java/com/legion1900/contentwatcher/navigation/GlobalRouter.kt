package com.legion1900.contentwatcher.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.legion1900.game_list_feature.GameListFragment
import com.legion1900.navigation.BaseRouter
import com.legion1900.navigation.Route
import com.legion1900.routes.GameList

class GlobalRouter(
    fm: FragmentManager,
    containerId: Int
) : BaseRouter(fm, containerId) {

    override fun resolveDestination(route: Route): Fragment? {
        return when (route) {
            is GameList -> GameListFragment.newInstance()
            else -> null
        }
    }
}
