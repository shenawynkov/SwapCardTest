package com.shenawynkov.data.repo

import android.util.Size
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.shenawynkov.data.mapToArtistDetail
import com.shenawynkov.data.mapToListOfArtists
import com.shenawynkov.data.model.ArtistQuery
import com.shenawynkov.data.model.ArtistsQuery
import com.shenawynkov.data.sharedPref.PrefManger
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.artist.Artist
import com.shenawynkov.domain.model.artist.ArtistDetail
import com.shenawynkov.domain.model.artist.ArtistsResult

import com.shenawynkov.domain.repositories.ArtistsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ArtistsRepoImpl(private val apolloClient: ApolloClient, val prefManger: PrefManger) :
    ArtistsRepo {
    private val pageSize = 15

    override fun getArtists(query: String, after: String?): Flow<Resource<ArtistsResult>> =
        flow {
            try {
                //emit loading until receiving data

                emit(Resource.Loading<ArtistsResult>())
                //emit  data
                val response =
                    apolloClient.query(
                        ArtistsQuery(
                            q = query,
                            pageSize,
                            Optional.presentIfNotNull(after)
                        )
                    ).execute()
                val list = response.data?.search?.artists?.nodes!!.mapToListOfArtists()
                val cursor = response.data?.search?.artists?.pageInfo?.endCursor
                emit(Resource.Success(ArtistsResult(list, cursor)))

            } catch (e: ApolloException) {
                emit(
                    Resource.Error<ArtistsResult>(
                        e.message ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<ArtistsResult>("Couldn't reach server. Check your internet connection."))
            }
        }

    override fun getArtistDetail(id: String): Flow<Resource<ArtistDetail>> = flow {
        try {
            //emit loading until receiving data

            emit(Resource.Loading<ArtistDetail>())
            //emit  data
            val response =
                apolloClient.query(
                    ArtistQuery(
                      id
                    )
                ).execute()
            val artistDetail = response.data?.node?.mapToArtistDetail()
            emit(Resource.Success(artistDetail!!))

        }  catch (e: IOException) {
            emit(Resource.Error<ArtistDetail>("Couldn't reach server. Check your internet connection."))
        }
        catch (e: Exception) {
            emit(
                Resource.Error<ArtistDetail>(
                    e.message ?: "An unexpected error occurred"
                )
            )
        }
    }

    override fun getFavCount() = prefManger.count

    override fun alterFav(key: String) = prefManger.alterBookmark(key)
    override fun getFav(key: String) = prefManger.isFav(key)


}