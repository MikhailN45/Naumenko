package com.application.naumenko.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.naumenko.R
import com.application.naumenko.data.Film
import com.application.naumenko.databinding.ViewHolderFilmListBinding
import com.bumptech.glide.Glide

class AdapterFilmList(private val filmClickListener: FilmClickListener) :
    RecyclerView.Adapter<FilmViewHolder>() {
    private var films: List<Film> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderFilmListBinding.inflate(inflater, parent, false)
        return FilmViewHolder(filmClickListener, binding)
    }

    override fun getItemCount(): Int = films.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
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

class FilmViewHolder(
    private val clickListener: AdapterFilmList.FilmClickListener,
    private val binding: ViewHolderFilmListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(film: Film) = with(binding) {
        Glide
            .with(binding.root)
            .load(film.poster)
            .into(favListSmallPoster)
        favListFilmTitle.text = film.title
        favListFilmYear.text = film.year
        favListFilmGenre.text = film.genres
        filmListFavoritesIcon.setImageResource(R.drawable.favs_icon)
        filmCard.setOnClickListener { clickListener.onFilmClick(film) }
    }
}