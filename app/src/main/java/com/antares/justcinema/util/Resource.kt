package com.antares.justcinema.util

sealed class Resource <out T> {
    data class Success<out T> (val value: T) : Resource<T>()
    data class Failure(
        val messageError: String?
    ) : Resource<Nothing>()
    object Loading: Resource<Nothing> ()
}