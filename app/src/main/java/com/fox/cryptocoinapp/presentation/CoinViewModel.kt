package com.fox.cryptocoinapp.presentation

import androidx.lifecycle.ViewModel
import com.fox.cryptocoinapp.domain.GetCoinInfoListUseCase
import com.fox.cryptocoinapp.domain.GetCoinInfoUseCase
import com.fox.cryptocoinapp.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)


    init {
        loadDataUseCase()
    }


}