package com.saiprasanth.stockapp.data.remote.dto

import com.squareup.moshi.Json
import retrofit2.http.Field

data class StockInfoDto(
    @field:Json(name = "Symbol") val symbol : String,
    @field:Json(name = "Description") val description : String,
    @field:Json(name = "Name") val name : String,
    @field:Json(name = "Country") val country : String,
    @field:Json(name = "Industry") val industry : String,
)
