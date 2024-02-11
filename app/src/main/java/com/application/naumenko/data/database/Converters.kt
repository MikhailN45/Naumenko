package com.application.naumenko.data.database

import androidx.room.TypeConverter
import com.application.naumenko.domain.model.About
import com.application.naumenko.domain.model.Country
import com.application.naumenko.domain.model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun <T> T.toJson(): String = Gson().toJson(this)

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, T::class.java)
class Converters {
    @TypeConverter
    fun filmToJson(it: About) = it.toJson()

    @TypeConverter
    fun filmFromJson(src: String): About = src.fromJson()

    @TypeConverter
    fun genreToJson(it: List<Genre>) = it.toJson()

    @TypeConverter
    fun genreFromJson(src: String): List<Genre> =
        Gson().fromJson(src, object : TypeToken<List<Genre>>() {}.type)

    @TypeConverter
    fun countryToJson(it: List<Country>) = it.toJson()

    @TypeConverter
    fun countryFromJson(src: String): List<Country> =
        Gson().fromJson(src, object : TypeToken<List<Country>>() {}.type)
}