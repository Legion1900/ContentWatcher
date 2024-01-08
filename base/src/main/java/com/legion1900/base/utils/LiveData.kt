package com.legion1900.base.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope

fun <R> ViewModel.wrapResult(block: suspend () -> R): LiveData<Result<R>> {
    return liveData(viewModelScope.coroutineContext) {
        val result = kotlin.runCatching { block() }
        emit(result)
    }
}

fun <R> Fragment.observe(ld: LiveData<R>, consume: (R) -> Unit) {
    ld.observe(viewLifecycleOwner, consume)
}
