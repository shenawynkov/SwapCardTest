package com.shenawynkov.swapcardtest.di

import com.apollographql.apollo3.ApolloClient
import com.shenawynkov.data.Constants.baseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    fun provideApollo() =ApolloClient.Builder()
        .serverUrl(baseUrl)
        .build()


}