package com.example.mysubmissionexpert.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv")
data class TvDbEntity(
    @PrimaryKey
    var id: Int,
    var poster_path: String?,
    var backdrop_path: String?,
    var original_title: String,
    var release_date: String,
    var overview: String,
    var isFavorite: Boolean = false,
    val vote_average: Double,
)