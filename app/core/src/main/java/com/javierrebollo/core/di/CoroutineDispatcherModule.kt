package com.javierrebollo.core.di

import com.javierrebollo.core.coroutine.MyCoroutineDispatcher
import com.javierrebollo.core.coroutines.MyCoroutineDispatcherImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutineDispatcherModule {

    @Singleton
    @Provides
    fun providesMyCoroutineDispatcher(): MyCoroutineDispatcher = MyCoroutineDispatcherImpl()
}
