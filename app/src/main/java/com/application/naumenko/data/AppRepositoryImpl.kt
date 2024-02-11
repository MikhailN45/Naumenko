package com.application.naumenko.data

import com.application.naumenko.data.database.FilmDetailsDao
import com.application.naumenko.data.database.model.FilmDetails
import com.application.naumenko.data.network.FilmApiResponse
import com.application.naumenko.domain.mappers.toAbout
import com.application.naumenko.domain.mappers.toFilms
import com.application.naumenko.domain.model.About
import com.application.naumenko.domain.model.Films
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val filmApiResponse: FilmApiResponse,
    private val filmDetailsDao: FilmDetailsDao
) : AppRepository {
    override suspend fun getTopFilms(): Films = filmApiResponse.getTopFilms().toFilms()

    override suspend fun getFilmDetails(id: Int): About =
        filmApiResponse.getFilmDetails(id).toAbout()

    override suspend fun selectId(id: Int): About? =
        filmDetailsDao.selectId(id)?.about

    override suspend fun getFavorites(): List<About> =
        filmDetailsDao.getFavorites().map { it.about }

    override suspend fun addFavorite(about: About) =
        filmDetailsDao.addFavorite(FilmDetails(about.kinopoiskId, about))

    override suspend fun removeFavorite(id: Int) =
        filmDetailsDao.removeFavorite(id)
}