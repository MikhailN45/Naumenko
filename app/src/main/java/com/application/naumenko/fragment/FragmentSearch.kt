package com.application.naumenko.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.naumenko.R
import com.application.naumenko.activity.MainActivity
import com.application.naumenko.adapters.AdapterSearch
import com.application.naumenko.data.Film
import com.application.naumenko.databinding.FragmentSearchBinding

class FragmentSearch : Fragment(), AdapterSearch.FilmClickListener {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AdapterSearch
    private lateinit var binding: FragmentSearchBinding
    private var films: List<Film> = listOf()

    companion object {
        const val TAG = "SearchFilmList"
        const val FILM_ID = "movieId"
        fun newInstance(): FragmentSearch = FragmentSearch()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        films = MainActivity.films
        recycler = binding.searchRecycler
        adapter = AdapterSearch(this)
        recycler.let {
            it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            it.adapter = adapter
            adapter.updateFilms(films)
        }
    }

    override fun onFilmClick(film: Film) {
        val bundle = Bundle()
        bundle.putInt(FILM_ID, film.id)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragment_container,
                FragmentFilmDetails.newInstance(bundle),
                FragmentFilmDetails.TAG
            )
            .addToBackStack(FragmentFilmDetails.TAG)
            .commit()
    }
}