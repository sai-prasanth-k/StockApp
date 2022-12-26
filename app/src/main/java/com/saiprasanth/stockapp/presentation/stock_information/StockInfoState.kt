package com.saiprasanth.stockapp.presentation.stock_information

import com.saiprasanth.stockapp.domin.model.IntradayInfo
import com.saiprasanth.stockapp.domin.model.StockInfo

data class StockInfoState(
    val stockInfo: List<IntradayInfo> = emptyList(),
    val stocks : StockInfo? = null,
    val isLoading : Boolean = false,
    val error : String? = null
)
