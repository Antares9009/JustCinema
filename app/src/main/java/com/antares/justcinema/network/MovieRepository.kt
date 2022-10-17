package com.antares.justcinema.network

import com.antares.justcinema.data.Movie
import com.antares.justcinema.data.MovieResponse

interface MovieRepository {

    suspend fun getUpcoming() : MovieResponse

    suspend fun getTopRated() : MovieResponse

    suspend fun getMovie(movieId: Int) : Movie
}