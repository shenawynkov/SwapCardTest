package com.shenawynkov.swapcardtest.ui.home.artists

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shenawynkov.swapcardtest.R
import com.shenawynkov.swapcardtest.databinding.FragmentArtistsBinding
import com.shenawynkov.swapcardtest.databinding.FragmentFavCountBinding
import com.shenawynkov.swapcardtest.ui.home.HomeViewModel
import com.shenawynkov.swapcardtest.ui.home.HomeViewModelFactory
import com.shenawynkov.swapcardtest.util.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavCountFragment : Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentFavCountBinding

    @Inject
    lateinit var factory: HomeViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_fav_count, container, false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         setUp(view)
    }

    override fun onResume() {
        super.onResume()
        binding.viewmodel = viewModel

    }

    fun setUp(view: View) {
        //viewmodel
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        //binding
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        //initViews


        }
        //search






}