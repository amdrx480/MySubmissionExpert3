package com.example.mysubmissionexpert.core.di

import androidx.room.Room
import com.example.mysubmissionexpert.core.data.source.local.room.TvDbDatabase
import com.example.mysubmissionexpert.core.data.source.remote.RemoteDataSource
import com.example.mysubmissionexpert.core.data.source.remote.network.ApiService
import com.example.mysubmissionexpert.core.domain.repository.ITvDbRepository
import com.example.mysubmissionexpert.core.utils.AppExecutors
import com.example.mysubmissionexpert.core.utils.Constanta
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CoreModule {
    val databaseModule = module {
        factory { get<TvDbDatabase>().tvDbDao() }
        single {
            val passphrase: ByteArray = SQLiteDatabase.getBytes("amdrx480".toCharArray())
            val factory = SupportFactory(passphrase)
            Room.databaseBuilder(
                androidContext(),
                TvDbDatabase::class.java, "TvDb.db"
            ).fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
        }
    }

    val networkModule = module {
        single {
            val hostname = "themoviedb.org"
            val certificatePinner = CertificatePinner.Builder()
                .add(hostname, "sha256/p+WeEuGncQbjSKYPSzAaKpF/iLcOjFLuZubtsXupYSI==")
                .build()
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
                .build()
        }
        single {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constanta.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(get())
                .build()
            retrofit.create(ApiService::class.java)
        }
    }

    val repositoryModule = module {
        single { com.example.mysubmissionexpert.core.data.source.local.LocalDataSource(get()) }
        single { RemoteDataSource(get()) }
        factory { AppExecutors() }
        single<ITvDbRepository> {
            com.example.mysubmissionexpert.core.data.TvDbRepository(
                get(),
                get(),
                get()
            )
        }
    }
}