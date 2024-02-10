package com.application.naumenko.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.naumenko.R
import com.application.naumenko.data.Film
import com.application.naumenko.databinding.ViewHolderSearchBinding
import com.bumptech.glide.Glide

class AdapterSearch(private val filmClickListener: FilmClickListener) : RecyclerView.Adapter<SearchViewHolder>() {
    private var films: List<Film> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderSearchBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int = films.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(films[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFilms(newFilms: List<Film>) {
        films = newFilms
        notifyDataSetChanged()
    }

    interface FilmClickListener {
        fun onFilmClick(film: Film)
    }
}

class SearchViewHolder(private val binding: ViewHolderSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(film: Film) = with(binding) {
        Glide
            .with(binding.root)
            .load(film.poster)
            .into(searchListSmallPoster)
        searchListFilmTitle.text = film.title
        searchListFilmYear.text = film.year
        searchListFilmGenre.text = film.genres
        searchListFavoritesIcon.setImageResource(R.drawable.favs_icon)
    }
}
