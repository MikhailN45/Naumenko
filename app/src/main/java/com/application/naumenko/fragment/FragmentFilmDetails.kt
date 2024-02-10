package com.application.naumenko.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.naumenko.activity.MainActivity
import com.application.naumenko.data.Film
import com.application.naumenko.databinding.FragmentFilmDetailsBinding
import com.bumptech.glide.Glide

class FragmentFilmDetails : Fragment() {

    private lateinit var film: Film
    private lateinit var binding: FragmentFilmDetailsBinding
    private var filmDetailsClick: FilmDetailsClick? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filmId = arguments?.getInt(FragmentFilmList.FILM_ID)
        film = MainActivity.films.single { it.id == filmId }

        with(binding) {
            filmDetailsBackButton.setOnClickListener { filmDetailsClick?.onBackClick() }
            Glide.with(root).load(film.poster).into(filmPoster)
            filmTitle.text = film.title
            filmStoryline.text = film.storyline
            filmGenresText.text = film.genres
            filmCountriesText.text = film.country
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FilmDetailsClick) {
            filmDetailsClick = context
        }
    }

    interface FilmDetailsClick {
        fun onBackClick()
    }

    companion object {
        const val TAG = "FragmentFilmDetails"
        fun newInstance(bundle: Bundle): FragmentFilmDetails {
            val fragment = FragmentFilmDetails()
            fragment.arguments = bundle
            return fragment
        }
    }
}