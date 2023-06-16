package com.legion1900.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

abstract class BaseRouter(
    private val fm: FragmentManager,
    @IdRes private val containerId: Int
) : Router {

    override fun route(route: Route): Boolean {
        val fragment = resolveDestination(route)
        return if (fragment != null) {
            navigate(fragment)
            true
        } else {
            false
        }
    }

    protected abstract fun resolveDestination(route: Route): Fragment?

    private fun navigate(fragment: Fragment) {
        fm.commit {
            replace(containerId, fragment)
        }
    }
}
