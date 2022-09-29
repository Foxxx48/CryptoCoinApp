package com.fox.cryptocoinapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.fox.cryptocoinapp.data.database.AppDatabase
import com.fox.cryptocoinapp.data.mapper.CoinMapper
import com.fox.cryptocoinapp.data.network.ApiFactory
import com.fox.cryptocoinapp.domain.CoinInfo
import com.fox.cryptocoinapp.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(application: Application): CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinInfoDao()
    private val mapper = CoinMapper()
    private val apiService = ApiFactory.apiService

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
       return Transformations.map( coinInfoDao.getPriceList()) {
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

    override suspend fun loadData() {
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
}