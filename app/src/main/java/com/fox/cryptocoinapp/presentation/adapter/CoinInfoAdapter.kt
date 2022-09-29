package com.fox.cryptocoinapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fox.cryptocoinapp.R
import com.fox.cryptocoinapp.data.network.ApiFactory.BASE_IMAGE_URL
import com.fox.cryptocoinapp.databinding.ActivityCoinPrceListBinding
import com.fox.cryptocoinapp.databinding.ItemCoinInfoBinding
import com.fox.cryptocoinapp.domain.CoinInfo
import com.fox.cryptocoinapp.utils.convertTimestampToTime
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(binding)
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
//        return CoinInfoViewHolder(view)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
                binding.tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                binding.tvPrice.text = price
                binding.tvLastUpdate.text =
                    String.format(lastUpdateTemplate, convertTimestampToTime(lastUpdate))
                Picasso.get().load(BASE_IMAGE_URL + imageUrl).into(binding.ivLogoCoin)
                itemView.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }

    inner class CoinInfoViewHolder(val binding: ItemCoinInfoBinding) : RecyclerView.ViewHolder(binding.root)


//    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val ivLogoCoin = itemView.ivLogoCoin
//        val tvSymbols = itemView.tvSymbols
//        val tvPrice = itemView.tvPrice
//        val tvLastUpdate = itemView.tvLastUpdate
//    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfo)
    }
}