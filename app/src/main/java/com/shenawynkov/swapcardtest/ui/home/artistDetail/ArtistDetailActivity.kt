package com.shenawynkov.swapcardtest.ui.home.artistDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.shenawynkov.swapcardtest.R
import com.shenawynkov.swapcardtest.databinding.ActivityArtistDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtistDetailActivity : AppCompatActivity() {
    companion object {
        val ID = "ID"
    }

    lateinit var viewModel: ArtistDetailViewModel
    lateinit var binding: ActivityArtistDetailBinding
    @Inject
    lateinit var factory: ArtistDetailViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()

    }


    private fun setUp() {
        //viewmodel
        viewModel = ViewModelProvider(owner = this@ArtistDetailActivity,factory)[ArtistDetailViewModel::class.java]
        viewModel.artistID.value = intent.getStringExtra(ID)
        //binding
        binding = DataBindingUtil.setContentView<ActivityArtistDetailBinding>(
            this@ArtistDetailActivity,
            R.layout.activity_artist_detail
        )
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        viewModel.artistID.value?.let { viewModel.updateArtistDetail(it) }

    }
}