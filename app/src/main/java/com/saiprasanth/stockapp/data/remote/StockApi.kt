package com.saiprasanth.stockapp.data.remote;

import com.saiprasanth.stockapp.data.remote.dto.StockInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListing(
        @Query("apikey") apikey : String = API_KEY
    ): ResponseBody

    @GET("query?function=TIME_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol : String,
        @Query("apikey") apiKey : String = API_KEY
    ):ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getStockInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY,
    ): StockInfoDto
    companion object{
        const val API_KEY = "GUXGN6YJAZ7YZHIP"
        const val BASE_URL = "https://alphavantage.co"

    }
}
