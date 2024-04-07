package com.example.calender.common

//sealed class Resources <T> (val data: T?= null, val message: String? = null) {
//    class Success<T>(data: T?) : Resources<T>(data)
//    class Error<T>(message: String?, data: T? = null): Resources<T>(data, message)
//    class Loading<T>(data: T? = null): Resources<T>(data)
//}
//
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String?) :
        ApiResult<Nothing>()
}