package com.antares.justcinema.di

import com.antares.justcinema.network.MovieRepository
import com.antares.justcinema.network.MovieRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(
        repository: MovieRepositoryImp
    ) : MovieRepository
}