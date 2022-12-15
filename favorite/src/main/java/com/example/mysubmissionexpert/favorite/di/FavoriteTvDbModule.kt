package com.example.mysubmissionexpert.favorite.di

import com.example.mysubmissionexpert.favorite.FavoriteTvDbViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class FavoriteTvDbModule {
    val favoriteTvDbModule = module(override = true) {
        viewModel { FavoriteTvDbViewModel(get()) }
    }
}