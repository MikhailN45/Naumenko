package com.application.naumenko.domain.interactors

import com.application.naumenko.domain.model.About
import com.application.naumenko.domain.model.Films

interface FilmInteractor {
    suspend fun getTopFilms(): Films

    suspend fun getFilmDetails(id: Int): About

    suspend fun selectId(id: Int): About?

    suspend fun getFavorites(): List<About>

    suspend fun addFavorite(about: About)

    suspend fun removeFavorite(id: Int)
}