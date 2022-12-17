package com.example.mysubmissionexpert.core.data.source.remote.network

import com.example.mysubmissionexpert.core.data.source.remote.response.ListTvDbResponse
import com.example.mysubmissionexpert.core.utils.Constanta
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("tv/popular")
    fun getList(
        @Query("api_key") apiKey: String = Constanta.API_KEY,
        @Query("language") language: String= Constanta.EN_US,
    ): Flowable<ListTvDbResponse>
}