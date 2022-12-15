package com.example.mysubmissionexpert.core.data.source.remote

import android.util.Log
import com.example.mysubmissionexpert.core.data.source.remote.network.ApiResponse
import com.example.mysubmissionexpert.core.data.source.remote.network.ApiService
import com.example.mysubmissionexpert.core.data.source.remote.response.TvDbResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RemoteDataSource(private val apiService: ApiService) {

    fun getAllTvDb(): Flowable<ApiResponse<List<TvDbResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<TvDbResponse>>>()

        //get data from remote api
        val client = apiService.getList()

        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.results
                resultData.onNext(if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}
