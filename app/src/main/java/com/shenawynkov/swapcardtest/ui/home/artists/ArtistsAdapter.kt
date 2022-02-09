package com.shenawynkov.swapcardtest.ui.home.artists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.shenawynkov.domain.model.artist.Artist

import com.shenawynkov.swapcardtest.R
import com.shenawynkov.swapcardtest.databinding.ItemArtistBinding
import com.shenawynkov.swapcardtest.ui.home.HomeViewModel

class ArtistsAdapter(
    private var list: List<Artist>,
    private val viewModel: HomeViewModel,
    private  val artistListener: ArtistListener
) : RecyclerView.Adapter<ArtistsAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: ItemArtistBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Any) {

            binding.setVariable(BR.obj, obj)
            binding.executePendingBindings()

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemArtistBinding>(
            layoutInflater, R.layout.item_artist, parent, false
        )
        return MyViewHolder(binding)
    }


    override fun getItemCount() = list.size


    fun setNewList(newList: List<Artist>) {
        this.list = newList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = getObjForPosition(position)
        obj.fav = viewModel.getFav(obj.id)
        holder.bind(obj)

        holder.binding.ivFav.setOnClickListener {
            val newFav = viewModel.alterFav(list[holder.adapterPosition].id)
            list[holder.adapterPosition].fav = newFav
            notifyItemChanged(holder.adapterPosition)
        }
        holder.binding.root.setOnClickListener {
           artistListener.onArtistSelected(list[holder.adapterPosition])
        }

    }

    private fun getObjForPosition(position: Int): Artist {
        return list.get(position)
    }

    interface ArtistListener {
        fun onArtistSelected(artist: Artist)

    }
}
