package com.example.calender.common

import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ApiService @Inject constructor(
    okHttpClient: OkHttpClient
) {

    private var retrofit: Retrofit? = null
    init {
        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://dev.frndapp.in:8085/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <S> buildService(service: Class<S>): S {
        return retrofit!!.create(service)
    }

    suspend fun <T> getApiResult(callApi: suspend () -> T): ApiResult<T> = try {
        ApiResult.Success(data = callApi())
    } catch (exception: Exception) {
        ApiResult.Error(message = exception.message)
    }

//    private fun getApiError(exception: Exception): String {
//        return when (exception) {
//            is HttpException -> {
//                try {
//                    val errorJsonString = exception.response()
//                        ?.errorBody()?.string()
//                    val errorString = JsonParser().parse(errorJsonString)
//                    return if (errorString
//                            .asJsonObject["message"]
//                            .asString.isNullOrEmpty()
//                    ) "Something Went wrong" else errorString
//                        .asJsonObject["message"]
//                        .asString
//
//                } catch (e: Exception) {
//                    "Some error occurred"
//                }
//            }
//
//            else -> {
//                return "No internet connection"
//            }
//        }
//    }
}