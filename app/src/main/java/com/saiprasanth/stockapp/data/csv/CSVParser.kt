package com.saiprasanth.stockapp.data.csv

import com.saiprasanth.stockapp.domin.model.StockListing
import java.io.InputStream

interface CSVParser<T> {
    suspend fun parse(stream :InputStream):List<StockListing>
}