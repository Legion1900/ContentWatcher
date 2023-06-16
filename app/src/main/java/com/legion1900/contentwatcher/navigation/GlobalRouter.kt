package com.legion1900.contentwatcher.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.legion1900.navigation.BaseRouter
import com.legion1900.navigation.Route

class GlobalRouter(
    fm: FragmentManager,
    containerId: Int
) : BaseRouter(fm, containerId) {

    override fun resolveDestination(route: Route): Fragment? {
        TODO("Not yet implemented")
    }
}
