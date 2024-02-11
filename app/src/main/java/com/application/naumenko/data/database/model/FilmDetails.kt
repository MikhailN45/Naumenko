package com.application.naumenko.data.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.application.naumenko.domain.model.About

@Entity
data class FilmDetails(
    @PrimaryKey val id: Int,
    @Embedded val about: About
)