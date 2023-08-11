package com.legion1900.base.exceptions

import java.io.IOException

interface AppException
class ConnectivityException(reason: Throwable) : IOException(
    "Connectivity issue:\n ${reason.stackTraceToString()}"
), AppException
