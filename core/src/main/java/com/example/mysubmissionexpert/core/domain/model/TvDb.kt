package com.example.mysubmissionexpert.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvDb(
    var id: Int,
    var poster_path: String?,
    var backdrop_path: String?,
    var original_title: String,
    var release_date: String,
    var overview: String,
    var isFavorite: Boolean,
    val vote_average: Double,
) : Parcelable