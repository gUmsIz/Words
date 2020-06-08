package com.gumsiz.words.data.utils


data class Resource<out T>(val status: Status, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, message = message)

        fun <T> loading(data: T?, message: String): Resource<T> =
            Resource(status = Status.LOADING, message = message)
    }
}