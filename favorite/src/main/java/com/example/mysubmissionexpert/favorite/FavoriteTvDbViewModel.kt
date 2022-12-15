package com.example.mysubmissionexpert.favorite

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.mysubmissionexpert.core.domain.usecase.TvDbUseCase

class FavoriteTvDbViewModel(tvDbUseCase: TvDbUseCase) : ViewModel() {
    val favoriteTvDb = LiveDataReactiveStreams.fromPublisher(tvDbUseCase.getFavoriteTvDb())
}