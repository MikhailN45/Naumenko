package com.application.naumenko.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.net.toUri
import java.io.File
import coil.ImageLoader
import coil.request.ImageRequest

fun savePictureToFile(name: String, url: String, context: Context) {

    val image = ImageRequest.Builder(context)

        .data(
            url.toUri().buildUpon().scheme("https").build()
        )

        .target { result ->
            val outputStream = File(context.filesDir, name).outputStream()
            (result as BitmapDrawable).bitmap
                .compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
        }.build()

    ImageLoader(context).enqueue(image)
}