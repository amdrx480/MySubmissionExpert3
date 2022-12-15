package com.example.mysubmissionexpert.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mysubmissionexpert.core.data.source.local.entity.TvDbEntity

@Database(entities = [TvDbEntity::class], version = 1, exportSchema = false)
abstract class TvDbDatabase : RoomDatabase() {
    abstract fun tvDbDao(): TvDbDao
}