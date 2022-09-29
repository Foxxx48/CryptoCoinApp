package com.fox.cryptocoinapp.domain

class GetCoinInfoUseCase(
    private val repository: CoinRepository,

) {
    operator fun invoke(fromSymbol: String) = repository.getCoinInfo(fromSymbol)
}