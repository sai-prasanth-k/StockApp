package com.saiprasanth.stockapp.di

import com.saiprasanth.stockapp.data.csv.CSVParser
import com.saiprasanth.stockapp.data.csv.IntradayInfoParser
import com.saiprasanth.stockapp.data.csv.StockListingParser
import com.saiprasanth.stockapp.domin.model.StockListing
import com.saiprasanth.stockapp.domin.repositiory.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStockListingParser(
        stockListingParser: StockListingParser
    ): CSVParser<StockListing>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfoParser>

    @Binds
    @Singleton
    abstract fun bindsStockRepository(
        stockRepository: StockRepository
    ):StockRepository
}