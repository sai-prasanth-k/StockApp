package com.saiprasanth.stockapp.presentation.stock_listing

sealed class StockListingEvent{
    object Refresh : StockListingEvent()
    data class  OnSearchEvent(val query: String): StockListingEvent()
}
