package com.example.mysubmissionexpert.core.domain.usecase

import com.example.mysubmissionexpert.core.domain.model.TvDb
import com.example.mysubmissionexpert.core.domain.repository.ITvDbRepository
import io.reactivex.Flowable

class TvDbInteractor(private val tvDbRepository: ITvDbRepository): TvDbUseCase {

    override fun getAllTvDb() = tvDbRepository.getAllTvDb()

    override fun getFavoriteTvDb(): Flowable<List<TvDb>> = tvDbRepository.getFavoriteTvDb()

    override fun setFavoriteTvDb(data: TvDb, state: Boolean) = tvDbRepository.setFavoriteTvDb(data, state)
}