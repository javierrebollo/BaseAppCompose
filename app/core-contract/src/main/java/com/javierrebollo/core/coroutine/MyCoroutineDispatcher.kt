package com.javierrebollo.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher

interface MyCoroutineDispatcher {
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
}
