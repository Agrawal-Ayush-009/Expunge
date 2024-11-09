package com.example.expunge

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://rnxjp-110-225-71-201.a.free.pinggy.link/")
    }

    @Provides
    @Singleton
    fun provideAPI(retrofitBuilder: Retrofit.Builder): API {
        return retrofitBuilder.build().create(API::class.java)
    }

}