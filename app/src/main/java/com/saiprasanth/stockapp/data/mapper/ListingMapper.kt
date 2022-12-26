package com.saiprasanth.stockapp.data.mapper

import com.saiprasanth.stockapp.data.local.StockListingEntity
import com.saiprasanth.stockapp.data.remote.dto.StockInfoDto
import com.saiprasanth.stockapp.domin.model.StockInfo
import com.saiprasanth.stockapp.domin.model.StockListing

fun StockListingEntity.toStockListing(): StockListing {

    return StockListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )

}

fun StockListing.toStockListingEntity(): StockListingEntity {

    return StockListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )

}

fun StockInfoDto.toStockInfo():StockInfo{
    return StockInfo(
        symbol = symbol?:"",
        description = description?:"",
        name =  name?:"",
        country = country?:"",
        industry = industry?:""
    )
}