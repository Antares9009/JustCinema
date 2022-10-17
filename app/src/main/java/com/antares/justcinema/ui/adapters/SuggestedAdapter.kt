package com.antares.justcinema.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antares.justcinema.data.Movie
import com.antares.justcinema.databinding.ItemSuggestedBinding
import com.antares.justcinema.util.Constants.IMAGE_PATH
import com.bumptech.glide.Glide

class SuggestedAdapter(private val suggestedMovies: MutableList<Movie>) : RecyclerView.Adapter<SuggestedAdapter.SuggestedViewHolder>() {

    private var onItemClickListener : ((Int) -> Unit)? = null

    inner class SuggestedViewHolder(var binding: ItemSuggestedBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(suggestedMovie: Movie){
            val urlPoster = IMAGE_PATH.plus(suggestedMovie.poster_path)
            binding.apply {
                Glide.with(ivSuggested.context).load(urlPoster).into(ivSuggested)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedViewHolder {
        return SuggestedViewHolder(ItemSuggestedBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SuggestedViewHolder, position: Int) {
        val movie = suggestedMovies[position]
        holder.bind(suggestedMovies[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(movie.id) }
        }
    }

    override fun getItemCount() = suggestedMovies.size

    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSuggestedList(results: List<Movie>){
        suggestedMovies.clear()
        suggestedMovies.addAll(results)
        notifyDataSetChanged()
    }
}