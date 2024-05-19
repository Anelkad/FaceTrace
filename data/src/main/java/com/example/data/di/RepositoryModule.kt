package com.example.data.di

import com.example.data.remote.FaceTraceApi
import com.example.data.repository.FaceTraceRepositoryImpl
import com.example.data.repository.LoginRepositoryImpl
import com.example.data.repository.SignUpRepositoryImpl
import com.example.domain.repository.FaceTraceRepository
import com.example.domain.repository.LoginRepository
import com.example.domain.repository.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
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

    @Provides
    @Singleton
    fun provideLoginRepository(
        auth: FirebaseAuth
    ): LoginRepository = LoginRepositoryImpl(auth)

    @Provides
    @Singleton
    fun provideSignUpRepository(
        auth: FirebaseAuth
    ): SignUpRepository = SignUpRepositoryImpl(auth)
}