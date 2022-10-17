package com.antares.justcinema.di

import com.antares.justcinema.BuildConfig
import com.antares.justcinema.data.Movie
import com.antares.justcinema.network.MovieApi
import com.antares.justcinema.network.MovieInterceptor
import com.antares.justcinema.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private fun createClient() : OkHttpClient {
        val okHttpClient : OkHttpClient.Builder =
            OkHttpClient.Builder().addInterceptor(MovieInterceptor())
        if(BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClient.addInterceptor(loggingInterceptor)
        }
        return okHttpClient.build()
    }
    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit) : MovieApi = retrofit.create(MovieApi::class.java)
}