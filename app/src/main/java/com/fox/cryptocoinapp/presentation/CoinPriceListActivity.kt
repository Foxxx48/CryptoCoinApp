package com.fox.cryptocoinapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fox.cryptocoinapp.databinding.ActivityCoinPrceListBinding
import com.fox.cryptocoinapp.domain.CoinInfo
import com.fox.cryptocoinapp.presentation.adapter.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private  var _binding: ActivityCoinPrceListBinding? = null
    private val binding get () = _binding!!

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCoinPrceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol
                )
                startActivity(intent)
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this, Observer {
            adapter.coinInfoList = it
        })
    }


}