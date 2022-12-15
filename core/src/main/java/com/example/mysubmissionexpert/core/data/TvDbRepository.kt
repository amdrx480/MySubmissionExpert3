package com.example.mysubmissionexpert.core.data

import com.example.mysubmissionexpert.core.data.source.local.LocalDataSource
import com.example.mysubmissionexpert.core.data.source.remote.RemoteDataSource
import com.example.mysubmissionexpert.core.data.source.remote.network.ApiResponse
import com.example.mysubmissionexpert.core.data.source.remote.response.TvDbResponse
import com.example.mysubmissionexpert.core.domain.model.TvDb
import com.example.mysubmissionexpert.core.domain.repository.ITvDbRepository
import com.example.mysubmissionexpert.core.utils.AppExecutors
import com.example.mysubmissionexpert.core.utils.DataMapper
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TvDbRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: com.example.mysubmissionexpert.core.data.source.local.LocalDataSource,
    private val appExecutors: AppExecutors
) : ITvDbRepository {

    override fun getAllTvDb(): Flowable<com.example.mysubmissionexpert.core.data.Resource<List<TvDb>>> =
        object : com.example.mysubmissionexpert.core.data.NetworkBoundResource<List<TvDb>, List<TvDbResponse>>() {
            override fun loadFromDB(): Flowable<List<TvDb>> {
                return localDataSource.getAllTvDb().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<TvDb>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): Flowable<ApiResponse<List<TvDbResponse>>> =
                remoteDataSource.getAllTvDb()

            override fun saveCallResult(data: List<TvDbResponse>) {
                val tvDbList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertTvDb(tvDbList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()

    override fun getFavoriteTvDb(): Flowable<List<TvDb>> {
        return localDataSource.getFavoriteTvDb().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteTvDb(data: TvDb, state: Boolean) {
        val tvDbEntity = DataMapper.mapDomainToEntity(data)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvDb(tvDbEntity, state) }
    }
}

