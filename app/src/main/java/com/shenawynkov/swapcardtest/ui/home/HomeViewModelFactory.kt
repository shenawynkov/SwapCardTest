package com.shenawynkov.swapcardtest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shenawynkov.domain.usecases.ArtistsUseCase
import com.shenawynkov.domain.usecases.FavUseCase

import javax.inject.Inject

class HomeViewModelFactory
@Inject constructor(
    private val artistsUseCase: ArtistsUseCase,
    private val favUseCase: FavUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            artistsUseCase,
            favUseCase
        ) as T
    }


}