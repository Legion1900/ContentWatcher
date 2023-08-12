package com.legion1900.base

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.legion1900.base.exceptions.WatcherException
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

    protected open fun onError(e: Throwable) {
        if (e !is WatcherException) {
            val className = this::class.qualifiedName
            Log.e(className, "Uncaught exception", e)
        }
    }

    protected fun <T> LiveData<Result<T>>.handleResult(onSuccess: (T) -> Unit) {
        observe(viewLifecycleOwner) { result ->
            result
                .onSuccess(onSuccess)
                .onFailure(::onError)
        }
    }
}
