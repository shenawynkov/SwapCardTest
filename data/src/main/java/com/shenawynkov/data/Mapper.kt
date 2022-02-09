package com.shenawynkov.data

import com.shenawynkov.data.model.ArtistQuery
import com.shenawynkov.data.model.ArtistsQuery
import com.shenawynkov.data.model.fragment.ArtistDetailsFragmentImpl_ResponseAdapter
import com.shenawynkov.domain.model.artist.Artist
import com.shenawynkov.domain.model.artist.ArtistDetail

fun List<ArtistsQuery.Node?>.mapToListOfArtists(): List<Artist> {
    return filterNotNull().map {
        Artist(
            it.artistBasicFragment.disambiguation,
            it.artistBasicFragment.id,
            it.artistBasicFragment.name
        )
    }
}

fun ArtistQuery.Node.mapToArtistDetail(): ArtistDetail? {
    artistDetailsFragment?.let {
        return ArtistDetail(
            id = it.id,
            name = it.name,
            disambiguation = it.disambiguation,
            country = it.country
        )

    }
    return null
}