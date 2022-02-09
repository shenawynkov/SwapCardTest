package com.shenawynkov.swapcardtest.ui.home.artistDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shenawynkov.data.model.ArtistsQuery
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.artist.Artist
import com.shenawynkov.domain.model.artist.ArtistDetail
import com.shenawynkov.domain.model.artist.ArtistsResult
import com.shenawynkov.domain.usecases.ArtistDetailUseCase
import com.shenawynkov.domain.usecases.ArtistsUseCase
import com.shenawynkov.domain.usecases.FavUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ArtistDetailViewModel(
    private val artistDetailUseCase: ArtistDetailUseCase,
    private val favUseCase: FavUseCase
) : ViewModel() {
    val artistID = MutableLiveData<String>()
    val artistDetail = MutableLiveData<ArtistDetail>()
    val loading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String>()


    fun updateArtistDetail(id: String) {


        artistDetailUseCase.invoke(id).onEach { result ->

            when (result) {
                is Resource.Success -> {
                    loading.value = false
                    val response = result.data

                    (response?.id)?.let {
                        response.fav = getFav(it)
                    }
                    artistDetail.value = response


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


    fun onFavClicked() {
        artistDetail.value?.id?.let { alterFav(it) }

        (artistDetail.value?.fav)?.let {
            artistDetail.value?.fav = !it
        }
        //update binded data
        artistDetail.value = artistDetail.value

    }


    fun alterFav(key: String) = favUseCase.alterFav(key)
    fun getFav(key: String) = favUseCase.getFav(key)


}