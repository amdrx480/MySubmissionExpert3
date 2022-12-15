package com.example.mysubmissionexpert.core.utils

import com.example.mysubmissionexpert.core.data.source.local.entity.TvDbEntity
import com.example.mysubmissionexpert.core.data.source.remote.response.TvDbResponse
import com.example.mysubmissionexpert.core.domain.model.TvDb

object DataMapper {
    fun mapResponsesToEntities(input: List<TvDbResponse>): List<TvDbEntity> {
        val tvDbList = ArrayList<TvDbEntity>()
        input.map {
            val tvDb = TvDbEntity(
                it.id,
                it.poster_path,
                it.backdrop_path,
                it.original_title,
                it.release_date,
                it.overview,
                isFavorite = false,
                it.vote_average
            )
            tvDbList.add(tvDb)
        }
        return tvDbList
    }

    fun mapEntitiesToDomain(input: List<TvDbEntity>): List<TvDb> =
        input.map {
            TvDb(
                it.id,
                it.poster_path,
                it.backdrop_path,
                it.original_title,
                it.release_date,
                it.overview,
                it.isFavorite,
                it.vote_average
            )
        }

    fun mapDomainToEntity(input: TvDb) = TvDbEntity(
        input.id,
        input.poster_path,
        input.backdrop_path,
        input.original_title,
        input.release_date,
        input.overview,
        input.isFavorite,
        input.vote_average
    )
}