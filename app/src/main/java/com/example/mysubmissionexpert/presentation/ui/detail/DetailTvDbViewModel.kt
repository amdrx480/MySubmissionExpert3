package com.example.mysubmissionexpert.presentation.ui.detail

import androidx.lifecycle.ViewModel
import com.example.mysubmissionexpert.core.domain.model.TvDb
import com.example.mysubmissionexpert.core.domain.usecase.TvDbUseCase

class DetailTvDbViewModel(private val tvDbUseCase: TvDbUseCase) : ViewModel() {
    fun setFavoriteTvDb(tvDb: TvDb, newStatus:Boolean) =
        tvDbUseCase.setFavoriteTvDb(tvDb, newStatus)
}