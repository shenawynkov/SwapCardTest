package com.shenawynkov.domain.model.artist

data class Artist(
    val disambiguation: String?,
    val id: String,
    val name: String?,
    var fav: Boolean =false
)