package com.javierrebollo.core.coroutines

import com.javierrebollo.core.coroutine.MyCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MyCoroutineDispatcherImpl : MyCoroutineDispatcher {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}
