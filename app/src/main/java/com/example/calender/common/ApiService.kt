package com.example.calender.common

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

}