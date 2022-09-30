package com.fox.cryptocoinapp

import android.app.Application
import androidx.work.Configuration
import com.fox.cryptocoinapp.data.database.AppDatabase
import com.fox.cryptocoinapp.data.mapper.CoinMapper
import com.fox.cryptocoinapp.data.network.ApiFactory
import com.fox.cryptocoinapp.data.network.ApiService
import com.fox.cryptocoinapp.data.workers.RefreshDataWorkerFactory

import com.fox.cryptocoinapp.di.DaggerAppComponent


class CoinApp: Application(), Configuration.Provider {

    val myComponent by lazy { DaggerAppComponent.factory()
        .create(this)

    }

    override fun getWorkManagerConfiguration(): Configuration {
       return Configuration.Builder()
           .setWorkerFactory(
               RefreshDataWorkerFactory(
                   AppDatabase.getInstance(this).coinInfoDao(),
                   CoinMapper(),
                   ApiFactory.apiService
               )
           ).build()
    }


}