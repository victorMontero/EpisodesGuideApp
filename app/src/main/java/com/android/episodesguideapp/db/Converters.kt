package com.android.episodesguideapp.db

import androidx.room.TypeConverter
import com.android.episodesguideapp.models.Image

class Converters {

    @TypeConverter
    fun fromImage(image: Image): String {
        return image.original
    }

    @TypeConverter
    fun toImage(url: String): Image{
        return Image(url)
    }
}