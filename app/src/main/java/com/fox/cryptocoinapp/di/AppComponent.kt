package com.fox.cryptocoinapp.di

import android.app.Application
import com.fox.cryptocoinapp.presentation.CoinDetailActivity
import com.fox.cryptocoinapp.presentation.CoinDetailFragment
import com.fox.cryptocoinapp.presentation.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: CoinPriceListActivity)

    fun inject(fragment: CoinDetailFragment)

    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}