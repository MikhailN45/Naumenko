package com.application.naumenko.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.application.naumenko.data.database.model.FilmDetails

@Dao
interface FilmDetailsDao {
    @Query("SELECT * FROM FilmDetails ORDER BY id DESC")
    suspend fun getFavorites(): List<FilmDetails>

    @Query("SELECT * FROM filmdetails WHERE id = :movieApiId")
    suspend fun selectId(movieApiId: Int): FilmDetails?

    @Query("DELETE FROM FilmDetails WHERE id = :movieApiId")
    suspend fun removeFavorite(movieApiId: Int)

    @Insert
    suspend fun addFavorite(filmDetails: FilmDetails)
}