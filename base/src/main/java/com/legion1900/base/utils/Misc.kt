package com.legion1900.base.utils

import androidx.annotation.ChecksSdkIntAtLeast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

@ChecksSdkIntAtLeast
fun ensureApiLevel(apiLevel: Int, block: () -> Unit) {
    if (android.os.Build.VERSION.SDK_INT >= apiLevel) block()
}

fun AppCompatActivity.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PermissionChecker.PERMISSION_GRANTED
}
