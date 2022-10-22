package com.antares.justcinema.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antares.justcinema.data.Movie
import com.antares.justcinema.databinding.ItemMovieBinding
import com.antares.justcinema.util.Constants.IMAGE_PATH
import com.bumptech.glide.Glide

class MoviesAdapter(private val moviesList: MutableList<Movie>) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>()  {

    private var onItemClickListener : ((Int) -> Unit)? = null



    inner class MoviesViewHolder(var binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie){
            val urlPoster = IMAGE_PATH.plus(movie.posterPath)
            binding.apply {
                Glide.with(ivMovie.context).load(urlPoster).into(ivMovie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.bind(moviesList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(movie.id) }
        }
    }

    override fun getItemCount() = moviesList.size

    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateMoviesList(results: List<Movie>){
        moviesList.clear()
        moviesList.addAll(results)
        notifyDataSetChanged()
    }

}