package com.application.naumenko.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.naumenko.R
import com.application.naumenko.data.Film
import com.application.naumenko.databinding.ViewHolderFavoritesBinding
import com.bumptech.glide.Glide

class AdapterFavorites(private val filmClickListener: FilmClickListener) :
    RecyclerView.Adapter<FavoritesViewHolder>() {
    private var films: List<Film> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderFavoritesBinding.inflate(inflater, parent, false)
        return FavoritesViewHolder(filmClickListener, binding)
    }

    override fun getItemCount(): Int = films.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
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

class FavoritesViewHolder(
    private val clickListener: AdapterFavorites.FilmClickListener,
    private val binding: ViewHolderFavoritesBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(film: Film) = with(binding) {
        Glide
            .with(binding.root)
            .load(film.poster)
            .into(favListSmallPoster)
        favListFilmTitle.text = film.title
        favListFilmYear.text = film.year
        favListFilmGenre.text = film.genres
        favListFavoritesIcon.setImageResource(R.drawable.favs_icon)
        favListCard.setOnClickListener { clickListener.onFilmClick(film) }
    }
}
