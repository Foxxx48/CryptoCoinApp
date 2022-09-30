package com.fox.cryptocoinapp.di

import android.app.Application
import com.fox.cryptocoinapp.CoinApp
import com.fox.cryptocoinapp.presentation.CoinDetailActivity
import com.fox.cryptocoinapp.presentation.CoinDetailFragment
import com.fox.cryptocoinapp.presentation.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class, ViewModelModule::class, WorkerModule::class])
interface AppComponent {

    fun inject(activity: CoinPriceListActivity)

    fun inject(fragment: CoinDetailFragment)

    fun inject(coinApp: CoinApp)

    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}