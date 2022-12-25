package com.saiprasanth.stockapp.presentation.stock_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saiprasanth.stockapp.domin.repositiory.StockRepository
import com.saiprasanth.stockapp.ui.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockListingViewModel @Inject constructor(
    private val repository: StockRepository
): ViewModel() {
    var state by mutableStateOf(StockListingState())

    private  var searchJob : Job? = null
    fun onEvent(event: StockListingEvent){
        when(event){
            is StockListingEvent.Refresh -> {
                getStockListings(fetchFromRemote = true)
            }
            is StockListingEvent.OnSearchEvent -> {
                state = state.copy(
                    searchQuery = event.query
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500)
                    getStockListings()
                }
            }
        }
    }

     private fun getStockListings(
        query : String = state.searchQuery.lowercase(),
        fetchFromRemote : Boolean = false
    ){
        viewModelScope.launch {
            repository.getStockListing(fetchFromRemote,query)
                .collect{
                    result ->
                    when(result){
                        is Resource.Success -> {
                            result.data?.let {
                                listings ->
                                state = state.copy(
                                    stocks = listings
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading ->{
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

}