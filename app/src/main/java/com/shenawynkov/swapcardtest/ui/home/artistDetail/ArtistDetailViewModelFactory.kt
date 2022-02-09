package com.shenawynkov.swapcardtest.ui.home.artistDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shenawynkov.domain.usecases.ArtistDetailUseCase
import com.shenawynkov.domain.usecases.ArtistsUseCase
import com.shenawynkov.domain.usecases.FavUseCase
import com.shenawynkov.swapcardtest.ui.home.HomeViewModel

import javax.inject.Inject

class ArtistDetailViewModelFactory
@Inject constructor(
    private val artistDetailUseCase: ArtistDetailUseCase,
    private val favUseCase: FavUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArtistDetailViewModel(
            artistDetailUseCase,
            favUseCase
        ) as T
    }


}