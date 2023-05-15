package com.legion1900.navigation

interface Route {
    val closeCurrentScreen: Boolean
}

interface Navigator {
    fun goTo(route: Route)
}
