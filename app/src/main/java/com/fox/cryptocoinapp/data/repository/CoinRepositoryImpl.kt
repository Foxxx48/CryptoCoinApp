package com.fox.cryptocoinapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.fox.cryptocoinapp.data.database.AppDatabase
import com.fox.cryptocoinapp.data.database.CoinInfoDao
import com.fox.cryptocoinapp.data.mapper.CoinMapper
import com.fox.cryptocoinapp.data.workers.RefreshDataWorker
import com.fox.cryptocoinapp.domain.CoinInfo
import com.fox.cryptocoinapp.domain.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val application: Application,
    private val coinInfoDao: CoinInfoDao,
    private val mapper: CoinMapper
    ) : CoinRepository {

//    private val coinInfoDao = AppDatabase.getInstance(application).coinInfoDao()



    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getCoinInfoModel(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )

    }
}