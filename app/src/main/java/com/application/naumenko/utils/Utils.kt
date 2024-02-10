package com.application.naumenko.utils

import com.application.naumenko.data.Genre

object Utils {
    fun getTags(genres: List<Genre>): String = genres.joinToString(", ") { it.genres }


}