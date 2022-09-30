package com.fox.cryptocoinapp

import android.app.Application
import androidx.work.Configuration
import com.fox.cryptocoinapp.data.workers.CoinWorkerFactory
import com.fox.cryptocoinapp.di.DaggerAppComponent
import javax.inject.Inject


class CoinApp: Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CoinWorkerFactory

    val myComponent by lazy { DaggerAppComponent.factory()
        .create(this)

    }

    override fun onCreate() {
        myComponent.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
       return Configuration.Builder()
           .setWorkerFactory(workerFactory)
           .build()
    }


}