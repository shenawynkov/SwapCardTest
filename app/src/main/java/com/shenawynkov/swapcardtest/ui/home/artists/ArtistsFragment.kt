package com.shenawynkov.swapcardtest.ui.home.artists

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shenawynkov.domain.model.artist.Artist
import com.shenawynkov.swapcardtest.R
import com.shenawynkov.swapcardtest.databinding.FragmentArtistsBinding
import com.shenawynkov.swapcardtest.ui.home.HomeViewModel
import com.shenawynkov.swapcardtest.ui.home.HomeViewModelFactory
import com.shenawynkov.swapcardtest.ui.home.artistDetail.ArtistDetailActivity
import com.shenawynkov.swapcardtest.util.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtistsFragment : Fragment(), ArtistsAdapter.ArtistListener {

    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentArtistsBinding
    lateinit var adapter: ArtistsAdapter

    @Inject
    lateinit var factory: HomeViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_artists, container, false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp(view)
    }

    override fun onResume() {
        super.onResume()
        viewModel.artists.value?.let { adapter.setNewList(it) }
    }
    fun setUp(view: View) {
        //viewmodel
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        //binding
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        //initViews
        adapter = ArtistsAdapter(ArrayList(), viewModel,this)
        binding.rvArtists.apply {

            adapter = this@ArtistsFragment.adapter
            layoutManager = LinearLayoutManager(context)
            //pagination
            addOnScrollListener(object :
                EndlessRecyclerViewScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    viewModel.updateArtists(false)
                }
            })


        }
        //search
        binding.edSearch.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    viewModel.updateArtists(newQuery = true)

                    return true
                }
                return false
            }
        })

        viewModel.artists.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setNewList(it)
            }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onArtistSelected(artist: Artist) {
        moveToArtistDetail(artist)

    }

    private fun moveToArtistDetail(artist: Artist) {
        val intent = Intent(context, ArtistDetailActivity::class.java)
        intent.putExtra(ArtistDetailActivity.ID, artist.id)
        context?.startActivity(intent)
    }

}