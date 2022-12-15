package com.example.mysubmissionexpert.di

import com.example.mysubmissionexpert.core.domain.usecase.TvDbInteractor
import com.example.mysubmissionexpert.core.domain.usecase.TvDbUseCase
import com.example.mysubmissionexpert.presentation.ui.detail.DetailTvDbViewModel
import com.example.mysubmissionexpert.presentation.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppModule {
    val useCaseModule = module {
        factory<TvDbUseCase> { TvDbInteractor(get()) }
    }

    val viewModelModule = module {
        viewModel { HomeViewModel(get()) }
        viewModel { DetailTvDbViewModel(get()) }
    }
}