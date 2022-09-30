package com.fox.cryptocoinapp.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.fox.cryptocoinapp.databinding.ItemCoinInfoBinding
import javax.inject.Inject

class CoinInfoViewHolder @Inject constructor(
    val binding: ItemCoinInfoBinding
) : RecyclerView.ViewHolder(binding.root)