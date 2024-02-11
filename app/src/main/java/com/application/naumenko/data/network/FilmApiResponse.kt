package com.application.naumenko.data.network

import com.application.naumenko.data.network.model.AboutResponse
import com.application.naumenko.data.network.model.FilmsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import javax.inject.Singleton

private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

@Singleton
interface FilmApiResponse {
    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopFilms(): FilmsResponse

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}")
    suspend fun getFilmDetails(@Path("id") id: Int): AboutResponse
}