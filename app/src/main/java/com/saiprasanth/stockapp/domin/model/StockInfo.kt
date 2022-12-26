package com.saiprasanth.stockapp.domin.model

import com.squareup.moshi.Json

data class StockInfo(
        val symbol : String?,
        val description : String?,
        val name : String?,
        val country : String?,
        val industry : String?,
)