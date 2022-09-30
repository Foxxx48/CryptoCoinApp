package com.fox.cryptocoinapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fox.cryptocoinapp.data.database.CoinInfoDbModel
import com.fox.cryptocoinapp.data.repository.CoinRepositoryImpl
import com.fox.cryptocoinapp.domain.GetCoinInfoListUseCase
import com.fox.cryptocoinapp.domain.GetCoinInfoUseCase
import com.fox.cryptocoinapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)


    init {
        loadDataUseCase()
    }


}