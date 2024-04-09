package com.example.data.di

import com.example.data.remote.FaceTraceApi
import com.example.data.repository.FaceTraceRepositoryImpl
import com.example.data.repository.FaceTraceRepositoryImplTest
import com.example.domain.repository.FaceTraceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePostRepository(
        api: FaceTraceApi
    ): FaceTraceRepository = FaceTraceRepositoryImpl(api)
}