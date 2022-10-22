package com.antares.justcinema.network

import com.antares.justcinema.data.Movie
import com.antares.justcinema.data.MovieResponse
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getUpcoming(): MovieResponse {
        return try {
            api.getUpcoming()
        } catch (e: Exception) {
            throw UpcomingException()
        }
    }

    override suspend fun getTopRated(): MovieResponse {
        return try {
            api.getTopRated()
        } catch (e: Exception){
            throw TopRatedException()
        }
    }

    override suspend fun getMovie(movieId: Int): Movie {
        return try {
            api.getMovie(movieId)
        } catch (e: Exception) {
            throw MovieException()
        }
    }
}

class UpcomingException() : RuntimeException()
class TopRatedException() : RuntimeException()
class MovieException() : RuntimeException()