package com.application.naumenko.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.load
import com.application.naumenko.R
import com.application.naumenko.databinding.FragmentFilmDetailsBinding
import com.application.naumenko.domain.model.About
import com.application.naumenko.presentation.states.FilmDetailsUiState
import com.application.naumenko.presentation.viewmodels.ViewModelFilmDetails
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class FragmentFilmDetails : Fragment() {

    private val viewModel: ViewModelFilmDetails by viewModels()
    private lateinit var binding: FragmentFilmDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFilmDetailsBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        setListeners()
    }

    private fun observeState() {
        viewModel.filmId = requireNotNull(requireArguments().getInt(FILM_ID))
        viewModel.state.observe(viewLifecycleOwner) { state ->
            val about: About? =
                if (state is FilmDetailsUiState.Success)
                    state.about
                else
                    null
            if (about != null) {
                with(binding) {
                    if (!(state as FilmDetailsUiState.Success).isDb) {
                        filmPoster.load(
                            about.posterUrl.toUri().buildUpon().scheme("https").build()
                        )
                    } else {
                        filmPoster.setImageURI(
                            File(
                                requireContext().filesDir,
                                requireContext().resources.getString(
                                    R.string.fileName,
                                    about.kinopoiskId
                                )
                            ).toUri()
                        )
                    }
                    filmStoryline.text = about.description
                    filmTitle.text = about.nameRu
                    var genres = ""
                    about.genres.forEachIndexed { index, it ->
                        if (index != 0) genres += ", "
                        genres += it.genre
                    }
                    filmGenresText.text = genres
                    var countries = ""
                    about.countries.forEachIndexed { index, it ->
                        if (index != 0) countries += ", "
                        countries += it.country
                    }
                    filmCountriesText.text = countries
                }
            }

            if (state is FilmDetailsUiState.Success) binding.filmGenresTitle.visibility =
                View.VISIBLE else View.GONE
            if (state is FilmDetailsUiState.Success) binding.filmCountriesTitle.visibility =
                View.VISIBLE else View.GONE

            with(binding.errorViewLayoutFilmDetails) {
                networkErrorView.visibility =
                    if (state is FilmDetailsUiState.Success) View.GONE else View.VISIBLE
                val imageRes =
                    if (state is FilmDetailsUiState.Loading) R.drawable.progressbar_animated else R.drawable.no_connect_error_icon
                noConnectionIcon.setImageResource(imageRes)
            }
        }
    }

    private fun setListeners() {
        binding.errorViewLayoutFilmDetails.retryConnectionButton.setOnClickListener { viewModel.onRetryButtonPressed() }
        binding.filmDetailsBackButton.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_filmDetailsFragment_to_filmListFragment)
        }
    }


    companion object {
        private const val FILM_ID = "id"

        fun createArguments(id: Int): Bundle {
            return bundleOf(FILM_ID to id)
        }
    }
}

