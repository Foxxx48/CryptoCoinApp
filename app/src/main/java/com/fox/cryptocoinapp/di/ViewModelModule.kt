package com.fox.cryptocoinapp.di

import androidx.lifecycle.ViewModel
import com.fox.cryptocoinapp.presentation.CoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    fun bindCoinViewModel(Impl: CoinViewModel): ViewModel

}
