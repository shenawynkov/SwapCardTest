package com.shenawynkov.domain.usecases

import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.artist.Artist
import com.shenawynkov.domain.repositories.ArtistsRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavUseCase @Inject constructor(private val artistsRepo: ArtistsRepo) {
      fun getFavCount() = artistsRepo.getFavCount()
      fun alterFav(key:String) = artistsRepo.alterFav(key)
      fun getFav(key:String) = artistsRepo.getFav(key)

}