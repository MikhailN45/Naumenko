package com.application.naumenko.data.network.model

data class FilmResponse(
    val filmId: Int,
    val nameRu: String,
    val posterUrl: String,
    val year: String,
    val genres: List<GenreResponse>,
    var isFavorite: Boolean = false
)

data class AboutResponse(
    val kinopoiskId: Int,
    val year: String,
    val nameRu: String,
    val posterUrl: String,
    val genres: List<GenreResponse>,
    val countries: List<CountryResponse>,
    val description: String,
    var isFavorite: Boolean = false
)

data class GenreResponse(
    val genre: String
)

data class CountryResponse(
    val country: String
)

data class FilmsResponse(
    val films: List<FilmResponse>
)
