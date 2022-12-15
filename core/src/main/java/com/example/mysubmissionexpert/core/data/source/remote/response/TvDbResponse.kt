package com.example.mysubmissionexpert.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvDbResponse(
    @SerializedName("id")
    var id: Int,

    @SerializedName("poster_path")
    var poster_path: String?,

    @SerializedName("backdrop_path")
    var backdrop_path: String?,

    @SerializedName("original_name")
    var original_title: String,

    @SerializedName("first_air_date")
    var release_date: String,

    @SerializedName("overview")
    var overview: String,

    @SerializedName("vote_average")
    val vote_average: Double,
)

data class ListTvDbResponse(
    @SerializedName("results")
    val results: List<TvDbResponse>
)

