package com.antares.justcinema.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antares.justcinema.data.Movie
import com.antares.justcinema.databinding.ItemTopRatedBinding
import com.antares.justcinema.util.Constants.IMAGE_PATH
import com.bumptech.glide.Glide

class TopRatedAdapter(private val topRatedMovies: MutableList<Movie>) : RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null

    inner class TopRatedViewHolder(var binding: ItemTopRatedBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(topRatedMovie: Movie){
            val urlPoster = IMAGE_PATH.plus(topRatedMovie.poster_path)
            binding.apply {
                Glide.with(ivTopRated.context).load(urlPoster).into(ivTopRated)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {
        return TopRatedViewHolder(ItemTopRatedBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        val movie = topRatedMovies[position]
        holder.bind(topRatedMovies[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(movie.id) }
        }
    }

    override fun getItemCount() = topRatedMovies.size

    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTopRatedList(results: List<Movie>){
        topRatedMovies.clear()
        topRatedMovies.addAll(results)
        notifyDataSetChanged()
    }

}