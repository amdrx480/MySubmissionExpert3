package com.example.mysubmissionexpert

import android.app.Application
import com.example.mysubmissionexpert.core.di.CoreModule
import com.example.mysubmissionexpert.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    CoreModule().databaseModule,
                    CoreModule().networkModule,
                    CoreModule().repositoryModule,
                    AppModule().useCaseModule,
                    AppModule().viewModelModule
                )
            )
        }
    }
}