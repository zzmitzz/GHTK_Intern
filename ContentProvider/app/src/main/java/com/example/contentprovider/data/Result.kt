package com.example.contentprovider.data

sealed class Result<out T> {
    data class Success<out T>(
        val data: T,
    ) : Result<T>()

    data class Fail(
        val error: Throwable,
    ) : Result<Nothing>()
}
