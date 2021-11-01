package com.example.newsreader.data.source.local

import androidx.room.TypeConverter
import com.example.newsreader.data.models.Source

class Converters {
    @TypeConverter
    fun sourceObjectToString(source: Source?): String {
        return "${source?.id}:${source?.name}"
    }

    @TypeConverter
    fun stringToSourceObject(string: String): Source {
        val id: String = string.split(":")[0]
        val name: String = string.split(":")[1]
        return Source(id, name)
    }
}