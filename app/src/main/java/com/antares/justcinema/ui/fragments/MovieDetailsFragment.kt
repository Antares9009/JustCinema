package com.antares.justcinema.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.antares.justcinema.data.Movie
import com.antares.justcinema.databinding.FragmentMovieDetailsBinding
import com.antares.justcinema.ui.MovieViewModel
import com.antares.justcinema.util.Constants
import com.antares.justcinema.util.Resource
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var _binding: FragmentMovieDetailsBinding
    private val binding
        get() = _binding!!
    private val viewModel by viewModels<MovieViewModel> {defaultViewModelProviderFactory}
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovieDetails()
        initObservers()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
    }

    private fun initObservers() {
        viewModel.detailMovie.observe(viewLifecycleOwner) { movie ->
            when (movie) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    handleSuccess(movie.value)
                }
                is Resource.Failure -> {
                    handleFailure(movie.messageError.toString())
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun getMovieDetails() {
        viewModel.getMovie(args.movieId)
    }

    private fun handleSuccess(movie: Movie) {
        val genres = StringBuilder()
        movie.genres.forEach {
            genres.append(" * ${it.name} ")
        }
        binding.apply {
            tvTitle.text = movie.title
            tvGenres.text = genres
            btnYear.text = movie.release_date.take(4)
            btnLanguage.text = movie.original_language.capitalize()
            btnRate.text = movie.vote_average.roundToInt().toString()
            tvOverview.text = movie.overview
            Glide.with(ivDetail.context).load(Constants.IMAGE_PATH_DETAILS.plus(movie.poster_path)).into(ivDetail)
        }
    }

    private fun handleFailure(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }


}