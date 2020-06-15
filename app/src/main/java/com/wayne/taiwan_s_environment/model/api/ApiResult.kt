package com.wayne.taiwan_s_environment.model.api

sealed class ApiResult<T> {
    companion object {
        fun <T> success(result: T?): ApiResult<T> {
            return when(result) {
                null -> Empty()
                else -> Success(result)
            }
        }

        fun <T> error(throwable: Throwable): ApiResult<T> {
            return Error(throwable)
        }
    }

    data class Success<T>(val result: T): ApiResult<T>()
    data class Error<T>(val throwable: Throwable): ApiResult<T>()

    class Empty<T>: ApiResult<T>()
}