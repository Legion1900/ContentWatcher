package com.legion1900.navigation

interface Router {

    /**
     * @return true if this router was abel to handle [route] otherwise false
     */
    fun route(route: Route): Boolean
}
