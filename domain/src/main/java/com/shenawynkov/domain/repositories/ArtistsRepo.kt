package com.shenawynkov.domain.repositories

import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.artist.Artist
import com.shenawynkov.domain.model.artist.ArtistDetail
import com.shenawynkov.domain.model.artist.ArtistsResult
import kotlinx.coroutines.flow.Flow

interface ArtistsRepo {
    fun getArtists(query:String,after:String?): Flow<Resource<ArtistsResult>>
    fun getArtistDetail(key:String): Flow<Resource<ArtistDetail>>
    fun getFavCount(): Int
    fun alterFav(key:String): Boolean
    fun getFav(key:String): Boolean
}