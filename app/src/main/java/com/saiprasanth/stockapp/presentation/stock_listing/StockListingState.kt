package com.saiprasanth.stockapp.presentation.stock_listing

import com.saiprasanth.stockapp.domin.model.StockListing

data class StockListingState(
    val stocks : List<StockListing> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val searchQuery : String = ""
)
