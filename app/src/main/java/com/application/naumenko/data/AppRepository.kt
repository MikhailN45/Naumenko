package com.application.naumenko.data

import com.application.naumenko.domain.model.About
import com.application.naumenko.domain.model.Films

interface AppRepository {
    suspend fun getTopFilms(): Films

    suspend fun getFilmDetails(id: Int): About

    suspend fun selectId(id: Int): About?

    suspend fun getFavorites(): List<About>

    suspend fun addFavorite(about: About)

    suspend fun removeFavorite(id: Int)
}