package com.example.movietheater.util

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromArrayListOfFloats(list: ArrayList<Float>?): String {
        return list?.joinToString(separator = ";") { it.toString() } ?: ""
    }

    @TypeConverter
    fun toArrayListOfFloats(string: String?): ArrayList<Float> {
        return ArrayList(string?.split(";")?.mapNotNull { it.toFloatOrNull() } ?: emptyList())
    }
}