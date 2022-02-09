package com.shenawynkov.swapcardtest.di

import com.apollographql.apollo3.ApolloClient
import com.shenawynkov.data.repo.ArtistsRepoImpl
import com.shenawynkov.data.sharedPref.PrefManger
import com.shenawynkov.domain.repositories.ArtistsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideArtistRepository(apolloClient: ApolloClient,prefManger: PrefManger): ArtistsRepo {
        return ArtistsRepoImpl(apolloClient,prefManger)
    }

}