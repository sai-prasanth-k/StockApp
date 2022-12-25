package com.saiprasanth.stockapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockListing(
        stockListingEntities : List<StockListingEntity>
    )

    @Query("DELETE FROM stocklistingentity")
    suspend fun clearStockListings()


    @Query("""
        SELECT * FROM stocklistingentity WHERE LOWER(name) Like "%" || LOWER(:query) || '%' OR
        UPPER(:query) == symbol
    """)
    suspend fun searchStockListing(query : String):List<StockListingEntity>
}