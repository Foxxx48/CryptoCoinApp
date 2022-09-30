package com.fox.cryptocoinapp.data.workers

import android.content.Context
import androidx.work.*
import com.fox.cryptocoinapp.data.database.AppDatabase
import com.fox.cryptocoinapp.data.database.CoinInfoDao
import com.fox.cryptocoinapp.data.mapper.CoinMapper
import com.fox.cryptocoinapp.data.network.ApiFactory
import com.fox.cryptocoinapp.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class RefreshDataWorker (
    context: Context,
    workerParameters: WorkerParameters,
    private val coinInfoDao: CoinInfoDao,
    private val mapper: CoinMapper,
    private val apiService: ApiService
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fSyms = mapper.mapNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
                val coinInfoListDto = mapper.mapJsonContainerToListCoinInfoDto(jsonContainer)
                val dbModelList = coinInfoListDto.map { mapper.mapDtoToDbModel(it) }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }

    companion object {

        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }

    class Factory @Inject constructor(
        private val coinInfoDao: CoinInfoDao,
        private val mapper: CoinMapper,
        private val apiService: ApiService
    ): ChildWorkerFactory {
        override fun crate(context: Context, workerParameters: WorkerParameters): ListenableWorker {
            return RefreshDataWorker(
                context,
                workerParameters,
                coinInfoDao,
                mapper,
                apiService
            )
        }
    }
}