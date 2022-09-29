package com.fox.cryptocoinapp.domain

class GetCoinInfoListUseCase(
    private val repository: CoinRepository
) {
    operator fun invoke() = repository.getCoinInfoList()
}