package com.javierrebollo.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MyCoroutineDispatcher {
    val main: CoroutineDispatcher
        get() = Dispatchers.Main
    val default: CoroutineDispatcher
        get() = Dispatchers.Default
    val io: CoroutineDispatcher
        get() = Dispatchers.IO
}
