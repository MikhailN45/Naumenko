package com.application.naumenko.domain.model

data class Film(
    val filmId: Int,
    val nameRu: String,
    val posterUrl: String,
    val year: String,
    val genres: List<Genre>,
    var isFavorite: Boolean = false
)

data class About(
    val kinopoiskId: Int,
    val year: String,
    val nameRu: String,
    val posterUrl: String,
    val genres: List<Genre>,
    val countries: List<Country>,
    val description: String,
    var isFavorite: Boolean = false
)

data class Genre(
    val genre: String
)

data class Country(
    val country: String
)

data class Films(
    val films: List<Film>
)
