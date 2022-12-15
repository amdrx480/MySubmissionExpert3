package com.example.mysubmissionexpert.presentation.ui.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.mysubmissionexpert.core.domain.usecase.TvDbUseCase

class HomeViewModel(tvDbUseCase: TvDbUseCase) : ViewModel() {
    val tvDb = LiveDataReactiveStreams.fromPublisher(tvDbUseCase.getAllTvDb())
}