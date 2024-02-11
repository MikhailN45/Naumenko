package com.application.naumenko.domain.interactors

import com.application.naumenko.data.AppRepository
import com.application.naumenko.domain.model.About
import com.application.naumenko.domain.model.Films
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmInteractorImpl @Inject constructor(
    private val appRepository: AppRepository
) : FilmInteractor {
    override suspend fun getTopFilms(): Films = appRepository.getTopFilms()

    override suspend fun getFilmDetails(id: Int): About = appRepository.getFilmDetails(id)

    override suspend fun selectId(id: Int): About? = appRepository.selectId(id)

    override suspend fun getFavorites(): List<About> = appRepository.getFavorites()

    override suspend fun addFavorite(about: About) = appRepository.addFavorite(about)

    override suspend fun removeFavorite(id: Int) = appRepository.removeFavorite(id)
}