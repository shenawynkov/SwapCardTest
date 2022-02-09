package com.shenawynkov.domain.model.artist

data class ArtistDetail(
    val disambiguation: String?,
    val id: String,
    val name: String?,
    val country: String?,
    var fav: Boolean =false
)