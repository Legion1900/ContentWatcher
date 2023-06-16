package com.legion1900.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.legion1900.navigation.MainHolder
import com.legion1900.navigation.Route
import com.legion1900.navigation.Router

abstract class WatcherFragment(@LayoutRes layoutId: Int) : Fragment(layoutId), Router {

    protected val mainHolder: MainHolder?
        get() = activity as? MainHolder

    protected fun requireMainHolder(): MainHolder = requireActivity() as MainHolder

    override fun route(route: Route): Boolean {
        return requireMainHolder().route(route)
    }
}
