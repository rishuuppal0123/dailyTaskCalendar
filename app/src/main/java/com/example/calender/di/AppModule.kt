package com.example.calender.di

import com.example.calender.api.TaskListApi
import com.example.calender.common.ApiService
import com.example.calender.repo.TaskListRepository
import com.example.calender.repo.repoImpl.TaskListRepoImpl
import com.example.calender.useCases.TaskListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
////    @Provides
////    @Singleton
////    fun provideApi(): TaskListApi {
////        return Retrofit.Builder()
////            .baseUrl("http://dev.frndapp.in:8085/")
////            .addConverterFactory(GsonConverterFactory.create())
////            .build()
////            .create(TaskListApi::class.java)
////    }
//
//    @Provides
//    @Singleton
//    fun provideTaskListRepo(taskListApi: TaskListApi): TaskListRepository {
//        return TaskListRepoImpl(apiService = ApiService(okHttpClient = OkHttpClient()))
//    }
//
//    @Provides
//    @Singleton
//    fun provideTaskListUseCase(taskListRepository: TaskListRepository): TaskListUseCase {
//        return TaskListUseCase(taskListRepository)
//    }
//}

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher