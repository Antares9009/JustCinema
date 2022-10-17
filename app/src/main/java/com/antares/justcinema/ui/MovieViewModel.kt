package com.antares.justcinema.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antares.justcinema.data.Movie
import com.antares.justcinema.data.MovieResponse
import com.antares.justcinema.network.MovieException
import com.antares.justcinema.network.MovieRepository
import com.antares.justcinema.network.TopRatedException
import com.antares.justcinema.network.UpcomingException
import com.antares.justcinema.util.Constants.GENERIC_ERROR
import com.antares.justcinema.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _upcomingMovies : MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    val upcomingMovies: LiveData<Resource<MovieResponse>>
        get() = _upcomingMovies

    private val _topRatedMovies: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    val topRatedMovies: LiveData<Resource<MovieResponse>>
        get() = _topRatedMovies

    private val _suggestedMovies: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    val suggestedMovies: LiveData<Resource<MovieResponse>>
        get() = _suggestedMovies

    private val _detailMovie: MutableLiveData<Resource<Movie>> = MutableLiveData()
    val detailMovie: LiveData<Resource<Movie>>
        get() = _detailMovie

    private val handlerException = CoroutineExceptionHandler{_, exception ->
        when(exception){
            is TopRatedException -> {
                _topRatedMovies.value = Resource.Failure(exception.message ?: GENERIC_ERROR)
                _suggestedMovies.value = Resource.Failure(exception.message ?: GENERIC_ERROR)
            }
            is UpcomingException -> {
                _upcomingMovies.value = Resource.Failure(exception.message ?: GENERIC_ERROR)
            }
            is MovieException -> {
                _detailMovie.value = Resource.Failure(exception.message ?: GENERIC_ERROR)
            }
        }
    }

    fun getUpcomingMovies() = viewModelScope.launch(handlerException) {
        _upcomingMovies.postValue(Resource.Loading)
        _upcomingMovies.postValue(Resource.Success(repository.getUpcoming()))
    }

    fun getTopRatedMovies() = viewModelScope.launch(handlerException) {
        _topRatedMovies.postValue(Resource.Loading)
        _topRatedMovies.postValue(Resource.Success(repository.getTopRated()))
    }

    fun getSuggestedMovies() = viewModelScope.launch(handlerException) {
        _suggestedMovies.postValue(Resource.Loading)
        _suggestedMovies.postValue(Resource.Success(repository.getTopRated()))
        Log.i("DetailsFragment",_suggestedMovies.value.toString())

    }

    fun getMovie(movieId: Int) = viewModelScope.launch(handlerException) {
        _detailMovie.postValue(Resource.Loading)
        _detailMovie.postValue(Resource.Success(repository.getMovie(movieId)))
        Log.i("DetailsFragment",_detailMovie.value.toString())
    }

    fun getMoviesFilter(filter: String = "hola"){
        var results: List<Movie>?
        viewModelScope.launch {
            results = repository.getTopRated().results.filter {
                it.release_date == "1972-03-14"
            }
            Log.i("Filter",results.toString())
        }
    }

}