package com.antares.justcinema.ui.fragments

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.antares.justcinema.data.MovieResponse
import com.antares.justcinema.databinding.FragmentMovieHomeBinding
import com.antares.justcinema.ui.MovieViewModel
import com.antares.justcinema.ui.adapters.SuggestedAdapter
import com.antares.justcinema.ui.adapters.TopRatedAdapter
import com.antares.justcinema.ui.adapters.UpcomingAdapter
import com.antares.justcinema.util.Constants.GENERIC_ERROR
import com.antares.justcinema.util.Resource
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieHomeFragment : Fragment() {

    private var _binding: FragmentMovieHomeBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModels<MovieViewModel> { defaultViewModelProviderFactory  }
    private val upcomingAdapter = UpcomingAdapter(mutableListOf())
    private val topRatedAdapter = TopRatedAdapter(mutableListOf())
    private val suggestedAdapter = SuggestedAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
        initViews()
        initClickListener()
        initObservers()
    }

    private fun initViewModels(){
        viewModel.getUpcomingMovies()
        viewModel.getTopRatedMovies()
        viewModel.getSuggestedMovies()
        viewModel.getMoviesFilter()
    }

    private fun initClickListener(){
        upcomingAdapter.setOnItemClickListener {
            val action = MovieHomeFragmentDirections.actionMovieHomeFragmentToMovieDetailsFragment(it)
            findNavController().navigate(action)
        }

        topRatedAdapter.setOnItemClickListener {
            val action = MovieHomeFragmentDirections.actionMovieHomeFragmentToMovieDetailsFragment(it)
            findNavController().navigate(action)
        }

        suggestedAdapter.setOnItemClickListener {
            val action = MovieHomeFragmentDirections.actionMovieHomeFragmentToMovieDetailsFragment(it)
            findNavController().navigate(action)
        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds[0])

            Log.i("FilterChip",group.toString())
            Log.i("FilterChip",checkedIds.toString())
            Log.i("FilterChip",chip.toString())

        }

        binding.chip1.setOnClickListener {
        }
    }

    private fun initViews(){
        binding.rvUpcoming.apply {
            adapter = upcomingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvTopRated.apply {
            adapter = topRatedAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvSuggested.apply {
            adapter = suggestedAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false
            )
        }
    }

    private fun initObservers() {
        viewModel.upcomingMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    handleUpcomingResponse(response.value)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    handleUpcomingFailure(response.messageError)
                }
            }
        }

        viewModel.topRatedMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    handleTopResponse(response.value)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    handleTopRatedFailure(response.messageError)
                }
            }
        }

        viewModel.suggestedMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    handleSuggestedResponse(response.value)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    handleSuggestedFailure(response.messageError)
                }
            }
        }


    }

    private fun handleSuggestedResponse(result: MovieResponse?) {
        suggestedAdapter.updateSuggestedList(result?.results ?: emptyList())
    }

    private fun handleSuggestedFailure(message: String?) {
        Snackbar.make(binding.rvSuggested, message ?: GENERIC_ERROR, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleTopResponse(results: MovieResponse?) {
        topRatedAdapter.updateTopRatedList(results?.results ?: emptyList())
    }

    private fun handleTopRatedFailure(message: String?) {
        Snackbar.make(binding.rvTopRated, message ?: GENERIC_ERROR, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleUpcomingResponse(result: MovieResponse?) {
        upcomingAdapter.updateUpcomingList(result?.results ?: emptyList())
    }

    private fun handleUpcomingFailure(message: String?) {
        Snackbar.make(binding.rvUpcoming, message ?: GENERIC_ERROR, Snackbar.LENGTH_SHORT).show()
    }

}