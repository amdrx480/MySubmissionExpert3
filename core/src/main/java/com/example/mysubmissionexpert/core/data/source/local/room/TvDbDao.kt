package com.example.mysubmissionexpert.core.data.source.local.room

import androidx.room.*
import com.example.mysubmissionexpert.core.data.source.local.entity.TvDbEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TvDbDao {

    @Query("SELECT * FROM tv")
    fun getAllTvDb(): Flowable<List<TvDbEntity>>

    @Query("SELECT * FROM tv where isFavorite = 1")
    fun getFavoriteTvDb(): Flowable<List<TvDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvDb(tvDb: List<TvDbEntity>): Completable

    @Update
    fun updateFavoriteTvDb(tvDb: TvDbEntity)
}