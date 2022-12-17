package com.example.mysubmissionexpert.core.domain.usecase

import com.example.mysubmissionexpert.core.domain.model.TvDb
import io.reactivex.Flowable

interface TvDbUseCase {
    fun getAllTvDb(): Flowable<com.example.mysubmissionexpert.core.data.Resource<List<TvDb>>>
    fun getFavoriteTvDb(): Flowable<List<TvDb>>
    fun setFavoriteTvDb(data: TvDb, state: Boolean)
}