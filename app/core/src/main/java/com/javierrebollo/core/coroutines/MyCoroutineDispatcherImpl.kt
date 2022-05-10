package com.javierrebollo.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MyCoroutineDispatcherImpl {
    val main: CoroutineDispatcher
        get() = Dispatchers.Main
    val default: CoroutineDispatcher
        get() = Dispatchers.Default
    val io: CoroutineDispatcher
        get() = Dispatchers.IO
}
