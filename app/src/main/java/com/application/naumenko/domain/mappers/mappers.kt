package com.application.naumenko.domain.mappers

import com.application.naumenko.data.network.model.AboutResponse
import com.application.naumenko.data.network.model.CountryResponse
import com.application.naumenko.data.network.model.FilmResponse
import com.application.naumenko.data.network.model.FilmsResponse
import com.application.naumenko.data.network.model.GenreResponse
import com.application.naumenko.domain.model.About
import com.application.naumenko.domain.model.Country
import com.application.naumenko.domain.model.Film
import com.application.naumenko.domain.model.Films
import com.application.naumenko.domain.model.Genre

fun FilmResponse.toFilm() = Film(
    filmId = filmId,
    nameRu = nameRu,
    posterUrl = posterUrl,
    year = year,
    genres = genres.toGenreList(),
    isFavorite = isFavorite
)

fun AboutResponse.toAbout() = About(
    kinopoiskId = kinopoiskId,
    year = year,
    nameRu = nameRu,
    posterUrl = posterUrl,
    genres = genres.toGenreList(),
    countries = countries.toCountryList(),
    description = description,
    isFavorite = isFavorite
)


fun GenreResponse.toGenre() = Genre(genre = genre)

fun List<GenreResponse>.toGenreList(): List<Genre> = map { it.toGenre() }

fun CountryResponse.toCountry() = Country(country = country)

fun List<CountryResponse>.toCountryList(): List<Country> = map { it.toCountry() }

fun List<FilmResponse>.toFilmList(): List<Film> = map { it.toFilm() }

fun FilmsResponse.toFilms() = Films(
    films = films.toFilmList()
)