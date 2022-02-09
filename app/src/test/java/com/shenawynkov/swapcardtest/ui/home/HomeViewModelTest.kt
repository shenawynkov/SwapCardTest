package com.shenawynkov.swapcardtest.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.artist.Artist
import com.shenawynkov.domain.model.artist.ArtistsResult
import com.shenawynkov.domain.usecases.ArtistsUseCase
import com.shenawynkov.domain.usecases.FavUseCase
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

class HomeViewModelTest {
    private lateinit var SUT: HomeViewModel
    private val artistsUseCase: ArtistsUseCase = mockk()
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

        SUT = HomeViewModel(artistsUseCase, favUseCase)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun updateArtistsNewQuery_success_dataStored_in_Artists() = testScope.runBlockingTest {

        //Arrange
        val artist = Artist("SS", "ss", "dd")
        val artists = listOf<Artist>(artist)
        val flow = flow<Resource<ArtistsResult>> {
            val result = ArtistsResult(artists, null)
            emit(Resource.Success(result))

        }
        SUT.query.value = "s"
        coEvery {
            artistsUseCase.invoke(any(), any())
        } returns flow
        every {
            favUseCase.getFav(any())
        } returns false

        //Act
        SUT.updateArtists(true)
        //Assert
        assertEquals(artists, SUT.artists.value)
        Assert.assertEquals(null, SUT.errorMessage.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateArtistsPaging_success_dataStored_in_Artists() = testScope.runBlockingTest {

        //Arrange
        val artistOld = Artist("dd", "dd", "2")
        val artist = Artist("SS", "ss", "dd")
        val artists = listOf<Artist>(artist)
        val flow = flow<Resource<ArtistsResult>> {
            val result = ArtistsResult(artists, null)
            emit(Resource.Success(result))

        }
        SUT.query.value = "s"
        SUT.artists.value?.add(artistOld)
        coEvery {
            artistsUseCase.invoke(any(), any())
        } returns flow
        every {
            favUseCase.getFav(any())
        } returns false

        //Act
        SUT.updateArtists(false)
        //Assert
        Truth.assertThat(SUT.artists.value).containsExactly(artistOld, artist)
        Assert.assertEquals(null, SUT.errorMessage.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateArtistsPaging_error_messagaUpdated() = testScope.runBlockingTest {

        //Arrange
        val error = "error"
        val flow = flow<Resource<ArtistsResult>> {

            emit(Resource.Error(error))

        }

        SUT.query.value = "s"
        coEvery {
            artistsUseCase.invoke(any(), any())
        } returns flow
        every {
            favUseCase.getFav(any())
        } returns false

        //Act
        SUT.updateArtists(false)
        //Assert
        Truth.assertThat(SUT.artists.value).isEmpty()
        Assert.assertEquals(error, SUT.errorMessage.value)
    }
}