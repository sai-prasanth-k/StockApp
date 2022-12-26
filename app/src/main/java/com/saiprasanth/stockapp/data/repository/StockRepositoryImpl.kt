package com.saiprasanth.stockapp.data.repository

import com.saiprasanth.stockapp.data.csv.CSVParser
import com.saiprasanth.stockapp.data.csv.IntradayInfoParser
import com.saiprasanth.stockapp.data.csv.StockListingParser
import com.saiprasanth.stockapp.data.local.StockDatabase
import com.saiprasanth.stockapp.data.mapper.toStockInfo
import com.saiprasanth.stockapp.data.mapper.toStockListing
import com.saiprasanth.stockapp.data.mapper.toStockListingEntity
import com.saiprasanth.stockapp.data.remote.StockApi
import com.saiprasanth.stockapp.domin.model.IntradayInfo
import com.saiprasanth.stockapp.domin.model.StockInfo
import com.saiprasanth.stockapp.domin.model.StockListing
import com.saiprasanth.stockapp.domin.repositiory.StockRepository
import com.saiprasanth.stockapp.ui.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api :StockApi,
    private val db : StockDatabase,
    private val stockListingParser: CSVParser<StockListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
): StockRepository{

    private val dao = db.dao
    override suspend fun getStockListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<StockListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchStockListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toStockListing() }
            ))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val justLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (justLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListing()
                stockListingParser.parse(response.byteStream())

            }catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null

            }catch (e : HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteListings?.let {
                listings ->
                emit(Resource.Success(listings))
                emit(Resource.Loading(false))
                dao.clearStockListings()
                dao.insertStockListing(
                    listings.map {
                        it.toStockListingEntity()
                    }
                )
                emit(Resource.Success(
                    data = dao.searchStockListing( "")
                        .map {
                            it.toStockListing()
                        }
                ))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val results = intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load the Intraday data"
            )
        }catch (e:HttpException){
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load the Intraday data"
            )
        }
     }

    override suspend fun getStockInfo(symbol: String): Resource<StockInfo> {
        return try {
            val result = api.getStockInfo(symbol)
            Resource.Success(result.toStockInfo())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load the Intraday data"
            )
        }catch (e:HttpException){
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load the Intraday data"
            )
        }
    }
}