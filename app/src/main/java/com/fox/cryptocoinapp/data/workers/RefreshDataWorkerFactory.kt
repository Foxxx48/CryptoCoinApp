package com.fox.cryptocoinapp.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.fox.cryptocoinapp.data.database.AppDatabase
import com.fox.cryptocoinapp.data.database.CoinInfoDao
import com.fox.cryptocoinapp.data.mapper.CoinMapper
import com.fox.cryptocoinapp.data.network.ApiFactory
import com.fox.cryptocoinapp.data.network.ApiService

class RefreshDataWorkerFactory(
    private val coinInfoDao: CoinInfoDao,
    private val mapper: CoinMapper,
    private val apiService: ApiService
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return RefreshDataWorker(
            appContext,
            workerParameters,
            coinInfoDao,
            mapper,
            apiService
        )
    }
}