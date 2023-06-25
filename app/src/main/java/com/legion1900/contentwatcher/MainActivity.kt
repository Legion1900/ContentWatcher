package com.legion1900.contentwatcher

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.legion1900.base.utils.ensureApiLevel
import com.legion1900.base.utils.hasPermission
import com.legion1900.contentwatcher.databinding.ActivityMainBinding
import com.legion1900.contentwatcher.navigation.GlobalRouter
import com.legion1900.navigation.MainHolder
import com.legion1900.navigation.Route
import com.legion1900.routes.GameList
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main), MainHolder {

    private val viewModel by viewModel<MainViewModel>()

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::handleNotifPermissionResult
    )

    private val globalRouter by lazy {
        GlobalRouter(supportFragmentManager, R.id.fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchHomeScreen()
        handleNotificationPermission()
    }

    private fun launchHomeScreen() {
        route(GameList())
    }

    override fun route(route: Route): Boolean = globalRouter.route(route)

    private fun handleNotificationPermission() {
        if (BuildConfig.DEBUG) {
            ensureApiLevel(Build.VERSION_CODES.TIRAMISU) {
                val hasNoPermission = !hasPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                if (hasNoPermission) {
                    requestPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun handleNotifPermissionResult(isGranted: Boolean) {
        if (!isGranted) {
            Log.e(MainActivity::class.qualifiedName, "Notification permission is missing; Chucker won't work")
        }
    }
}
