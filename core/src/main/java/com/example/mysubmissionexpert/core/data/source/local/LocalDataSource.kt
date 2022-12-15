package com.example.mysubmissionexpert.core.data.source.local

import com.example.mysubmissionexpert.core.data.source.local.entity.TvDbEntity
import com.example.mysubmissionexpert.core.data.source.local.room.TvDbDao
import io.reactivex.Flowable

class LocalDataSource(private val tvDbDao: TvDbDao) {

    fun getAllTvDb(): Flowable<List<TvDbEntity>> = tvDbDao.getAllTvDb()

    fun getFavoriteTvDb(): Flowable<List<TvDbEntity>> = tvDbDao.getFavoriteTvDb()

    fun insertTvDb(tvDbList: List<TvDbEntity>) = tvDbDao.insertTvDb(tvDbList)

    fun setFavoriteTvDb(tvDb: TvDbEntity, newState: Boolean) {
        tvDb.isFavorite = newState
        tvDbDao.updateFavoriteTvDb(tvDb)
    }
}