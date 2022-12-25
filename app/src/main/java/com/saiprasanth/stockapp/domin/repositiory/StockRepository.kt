package com.saiprasanth.stockapp.domin.repositiory

import com.saiprasanth.stockapp.domin.model.StockListing
import com.saiprasanth.stockapp.ui.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getStockListing(
        fetchFromRemote : Boolean,
        query:String

    ):Flow<Resource<List<StockListing>>>
}