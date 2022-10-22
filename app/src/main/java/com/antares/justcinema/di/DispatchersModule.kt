package com.antares.justcinema.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @DefaultDispatcher
    @Singleton
    @Provides
    fun providesDefaultDispatcher() : CoroutineDispatcher = Dispatchers.Default

    @IODispatcher
    @Singleton
    @Provides
    fun providesIODispatcher() : CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Singleton
    @Provides
    fun providesMainDispatcher() : CoroutineDispatcher = Dispatchers.Main

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IODispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher