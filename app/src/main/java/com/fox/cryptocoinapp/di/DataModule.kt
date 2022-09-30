package com.fox.cryptocoinapp.di

import android.app.Application
import com.fox.cryptocoinapp.data.database.AppDatabase
import com.fox.cryptocoinapp.data.database.CoinInfoDao
import com.fox.cryptocoinapp.data.repository.CoinRepositoryImpl
import com.fox.cryptocoinapp.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindsCoinRepository(impl: CoinRepositoryImpl) : CoinRepository

    companion object{
        @Provides
        fun provideCoinInfoDao(application: Application): CoinInfoDao {
            return AppDatabase.getInstance(application).coinInfoDao()
        }
    }


}