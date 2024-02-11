package com.application.naumenko.data.database

import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.RoomDatabase
import com.application.naumenko.data.database.model.FilmDetails

@Database(entities = [FilmDetails::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDetailDao(): FilmDetailsDao
}