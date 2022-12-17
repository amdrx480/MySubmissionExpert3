package com.example.mysubmissionexpert.core.data

import com.example.mysubmissionexpert.core.data.source.remote.network.ApiResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = PublishSubject.create<com.example.mysubmissionexpert.core.data.Resource<ResultType>>()
    private val mCompositeDisposable = CompositeDisposable()

    init {
        @Suppress("LeakingThis")
        val dbSource = loadFromDB()
        val db = dbSource
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { value ->
                dbSource.unsubscribeOn(Schedulers.io())
                if (shouldFetch(value)) {
                    fetchFromNetwork()
                } else {
                    result.onNext(com.example.mysubmissionexpert.core.data.Resource.Success(value))
                }
            }
        mCompositeDisposable.add(db)
    }
    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flowable<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): Flowable<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)
    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        result.onNext(com.example.mysubmissionexpert.core.data.Resource.Loading(null))
        val response = apiResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe{ response ->
                when(response){
                    is ApiResponse.Success -> {
                        saveCallResult(response.data)
                        val dbSource = loadFromDB()
                        dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe {
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(
                                    com.example.mysubmissionexpert.core.data.Resource.Success(
                                        it
                                    )
                                )
                            }
                    }
                    is ApiResponse.Empty -> {
                        val dbSource = loadFromDB()
                        dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe {
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(
                                    com.example.mysubmissionexpert.core.data.Resource.Success(
                                        it
                                    )
                                )
                            }
                    }
                    is ApiResponse.Error -> {
                        onFetchFailed()
                        result.onNext(
                            com.example.mysubmissionexpert.core.data.Resource.Error(
                                response.errorMessage,
                                null
                            )
                        )
                    }
                }
            }
        mCompositeDisposable.add(response)
    }
    fun asFlowable(): Flowable<com.example.mysubmissionexpert.core.data.Resource<ResultType>> =
        result.toFlowable(BackpressureStrategy.BUFFER)
}