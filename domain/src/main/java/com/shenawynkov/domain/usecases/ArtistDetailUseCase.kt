package com.shenawynkov.domain.usecases

import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.artist.Artist
import com.shenawynkov.domain.repositories.ArtistsRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArtistDetailUseCase @Inject constructor(private val artistsRepo: ArtistsRepo) {
     operator fun invoke(id: String) = artistsRepo.getArtistDetail(id)

}