package com.saiprasanth.stockapp.presentation.stock_information

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saiprasanth.stockapp.domin.repositiory.StockRepository
import com.saiprasanth.stockapp.ui.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockInfoViewModel @Inject constructor(
    private val savedStateHandle : SavedStateHandle,
    private val repository: StockRepository
): ViewModel() {
        var state by mutableStateOf(StockInfoState())
    init {
        viewModelScope.launch {
            val symbol =savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            val stockInfoResult = async{repository.getStockInfo(symbol)}
            val intradayInfoResult = async { repository.getIntradayInfo(symbol)}
            when(val result =stockInfoResult.await()){
                is Resource.Success ->{
                    state = state.copy(
                        stocks = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error ->{
                    state = state.copy(
                        stocks = null,
                        isLoading = false,
                        error = result.message
                    )
                }else -> Unit
            }
            when(val result =intradayInfoResult.await()){
                is Resource.Success ->{
                    state = state.copy(
                        stockInfo = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error ->{
                    state = state.copy(
                        stockInfo = null ?: emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                }else -> Unit
            }
        }
    }
}