package com.fox.cryptocoinapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fox.cryptocoinapp.CoinApp
import com.fox.cryptocoinapp.R
import com.fox.cryptocoinapp.databinding.ActivityCoinPriceListBinding

import com.fox.cryptocoinapp.domain.CoinInfo
import com.fox.cryptocoinapp.presentation.adapter.CoinInfoAdapter
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    private  var _binding: ActivityCoinPriceListBinding? = null
    private val binding get () = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory



    private val myComponent by lazy {
        (application as CoinApp).myComponent
//            .activityComponentFactory()
//            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        myComponent.inject(this)

        super.onCreate(savedInstanceState)

        _binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfo) {
                if (isOnePaneMode()) {
                    launchDetailActivity(coinInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinInfo.fromSymbol)
                }
            }
        }
        binding.rvCoinPriceList.adapter = adapter
//      If not need animation in recyclerView
        binding.rvCoinPriceList.itemAnimator = null
        val coinViewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
        coinViewModel.coinInfoList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailFragment(fromSymbol: String){
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }

    private fun launchDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(this, fromSymbol)
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}