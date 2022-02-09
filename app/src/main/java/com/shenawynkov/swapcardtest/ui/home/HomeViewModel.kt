package com.shenawynkov.swapcardtest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shenawynkov.data.model.ArtistsQuery
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.artist.Artist
import com.shenawynkov.domain.model.artist.ArtistsResult
import com.shenawynkov.domain.usecases.ArtistsUseCase
import com.shenawynkov.domain.usecases.FavUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(
    private val artistsUseCase: ArtistsUseCase,
    private val favUseCase: FavUseCase
) : ViewModel() {
    val artistsResult = MutableLiveData<ArtistsResult>()
    val artists = MutableLiveData<ArrayList<Artist>>(ArrayList())
    val loading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String>()
    val query = MutableLiveData<String>()


    fun updateArtists(newQuery: Boolean) {
        var cursor = artistsResult.value?.cursor
        val query = this.query.value
        if(query.isNullOrEmpty())
        {
            errorMessage.value="search can't be empty"
            return
        }
        if (newQuery) {
            cursor = null
            artists.value?.clear()
        }

        query.let { q ->
            artistsUseCase.invoke(q, cursor).onEach { result ->

                when (result) {
                    is Resource.Success -> {
                        loading.value = false
                        artistsResult.value = result.data
                        artistsResult.value?.artists?.let { artists.value?.addAll(it) }
                        artists.value = artists.value

                    }
                    is Resource.Error -> {
                        loading.value = false
                        errorMessage.value = result.message

                    }
                    is Resource.Loading -> {
                        loading.value = true

                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getFavCount() = favUseCase.getFavCount()
    fun alterFav(key: String) = favUseCase.alterFav(key)
    fun getFav(key: String) = favUseCase.getFav(key)


}