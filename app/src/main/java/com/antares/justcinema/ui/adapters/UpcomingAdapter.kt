package com.antares.justcinema.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.antares.justcinema.data.Movie
import com.antares.justcinema.databinding.ItemUpcomingBinding
import com.antares.justcinema.util.Constants.IMAGE_PATH
import com.bumptech.glide.Glide

class UpcomingAdapter(private val upcomingMovies: MutableList<Movie>) : RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null

    inner class UpcomingViewHolder(var binding: ItemUpcomingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(upcomingMovie: Movie) {
            val urlPoster = IMAGE_PATH.plus(upcomingMovie.poster_path)
            binding.apply {
                Glide.with(ivUpcoming.context).load(urlPoster).into(ivUpcoming)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        return UpcomingViewHolder(ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val movie = upcomingMovies[position]
        holder.bind(upcomingMovies[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(movie.id) }
        }
    }


    override fun getItemCount() = upcomingMovies.size

    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUpcomingList(results: List<Movie>){
        upcomingMovies.clear()
        upcomingMovies.addAll(results)
        notifyDataSetChanged()
    }

}