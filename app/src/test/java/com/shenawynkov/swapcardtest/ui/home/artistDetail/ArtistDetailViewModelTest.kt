package com.shenawynkov.swapcardtest.ui.home.artistDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.artist.Artist
import com.shenawynkov.domain.model.artist.ArtistDetail
import com.shenawynkov.domain.model.artist.ArtistsResult
import com.shenawynkov.domain.usecases.ArtistDetailUseCase
import com.shenawynkov.domain.usecases.ArtistsUseCase
import com.shenawynkov.domain.usecases.FavUseCase
import com.shenawynkov.swapcardtest.ui.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test

class ArtistDetailViewModelTest {
    private lateinit var SUT: ArtistDetailViewModel
    private val artistDetailUseCase: ArtistDetailUseCase = mockk()
    private val favUseCase: FavUseCase = mockk()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)

        SUT = ArtistDetailViewModel(artistDetailUseCase, favUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateArtistDetail_success_ArtistDetailUpdated() = testScope.runBlockingTest {
        //Arrange
        val artist = ArtistDetail("SS", "ss", "dd", "eg")
        val flow = flow<Resource<ArtistDetail>> {
            emit(Resource.Success(artist))

        }
        coEvery {
            artistDetailUseCase.invoke(any())
        } returns flow
        every {
            favUseCase.getFav(any())
        } returns false
        //Act
        SUT.updateArtistDetail("1")
        //Assert
        assertEquals(artist, SUT.artistDetail.value)
        Assert.assertEquals(null, SUT.errorMessage.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateArtistDetail_error_ArtistDetailUpdated() = testScope.runBlockingTest {
        //Arrange
        val error = "error"
        val flow = flow<Resource<ArtistDetail>> {
            emit(Resource.Error(error))

        }
        coEvery {
            artistDetailUseCase.invoke(any())
        } returns flow
        every {
            favUseCase.getFav(any())
        } returns false
        //Act
        SUT.updateArtistDetail("1")
        //Assert
        Truth.assertThat(SUT.artistDetail.value).isNull()
        Assert.assertEquals(error, SUT.errorMessage.value)
    }


}