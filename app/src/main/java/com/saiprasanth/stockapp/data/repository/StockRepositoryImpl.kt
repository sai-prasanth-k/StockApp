package com.saiprasanth.stockapp.data.repository

import com.saiprasanth.stockapp.data.local.StockDatabase
import com.saiprasanth.stockapp.data.mapper.toStockListing
import com.saiprasanth.stockapp.data.remote.StockApi
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
    val api :StockApi,
    val db : StockDatabase
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
                response.byteStream()

            }catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))

            }catch (e : HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            }
        }
    }
}