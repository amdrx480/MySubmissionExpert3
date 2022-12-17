package com.example.mysubmissionexpert.core.utils

import com.example.mysubmissionexpert.core.BuildConfig

class Constanta {
    companion object{
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val TV_BACKDROP = "https://image.tmdb.org/t/p/w500"
        const val TV_POSTER = "https://image.tmdb.org/t/p/original"
        const val EN_US = "en-US"
    }
}