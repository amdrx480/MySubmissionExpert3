package com.example.mysubmissionexpert.core.domain.repository

import androidx.lifecycle.LiveData
import com.example.mysubmissionexpert.core.data.Resource
import com.example.mysubmissionexpert.core.domain.model.TvDb
import io.reactivex.Flowable

interface ITvDbRepository {

    fun getAllTvDb(): Flowable<com.example.mysubmissionexpert.core.data.Resource<List<TvDb>>>

    fun getFavoriteTvDb(): Flowable<List<TvDb>>

    fun setFavoriteTvDb(data: TvDb, state: Boolean)

}