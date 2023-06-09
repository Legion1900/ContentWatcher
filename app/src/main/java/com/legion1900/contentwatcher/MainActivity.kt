package com.legion1900.contentwatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.legion1900.contentwatcher.databinding.ActivityMainBinding
import com.legion1900.contentwatcher.navigation.GlobalRouter
import com.legion1900.navigation.MainHolder
import com.legion1900.navigation.Route
import com.legion1900.routes.GameList
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main), MainHolder {

    private val viewModel by viewModel<MainViewModel>()

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val globalRouter by lazy {
        GlobalRouter(supportFragmentManager, R.id.fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchHomeScreen()
    }

    private fun launchHomeScreen() {
        route(GameList())
    }

    override fun route(route: Route): Boolean = globalRouter.route(route)
}
