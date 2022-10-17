package com.antares.justcinema.network

import com.antares.justcinema.data.Movie
import com.antares.justcinema.data.MovieResponse
import com.antares.justcinema.util.Constants.LANGUAGE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1
    ) : MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1
    ) : MovieResponse

    @GET("movie/{movieId}")
    suspend fun getMovie(
        @Path("movieId") movieId : Int,
        @Query("language") language: String = LANGUAGE
    ) : Movie
}