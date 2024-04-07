package com.example.calender.di

import com.example.calender.repo.TaskListRepository
import com.example.calender.repo.repoImpl.TaskListRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class TaskListModule {
    @Binds
    abstract fun provideTaskListRepo(taskListRepoImpl: TaskListRepoImpl): TaskListRepository
}