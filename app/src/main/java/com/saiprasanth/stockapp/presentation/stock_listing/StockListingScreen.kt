package com.saiprasanth.stockapp.presentation.stock_listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.saiprasanth.stockapp.presentation.destinations.StockInfoScreenDestination
import com.saiprasanth.stockapp.presentation.stock_information.DetailScreen

@Composable
@RootNavGraph(start = true)
@Destination
fun StockListingScreen(
        navigator: DestinationsNavigator,
        viewModel: StockListingViewModel
){
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)
        val state = viewModel.state

        Column(modifier = Modifier.fillMaxSize()) {

                OutlinedTextField(
                        value =state.searchQuery ,
                        onValueChange = {
                                viewModel.onEvent(
                                        StockListingEvent.OnSearchEvent(it)
                                )
                        } ,
                        modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                        placeholder = {
                                Text(text = "Search here ...")
                        },
                        maxLines = 1,
                        singleLine = true
                )
                SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = {
                                viewModel.onEvent(StockListingEvent.Refresh)
                        }
                ) {
                        LazyColumn(
                                modifier = Modifier.fillMaxSize()
                        ){
                                items(state.stocks.size){
                                        i ->
                                        val stock = state.stocks[i]
                                        StockItem(
                                                stock = stock,
                                                modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clickable {
                                                                 navigator.navigate(
                                                                         StockInfoScreenDestination(stock.symbol)
                                                                 )
                                                        }
                                                        .padding(16.dp)
                                        )
                                        if (i < state.stocks.size){
                                                Divider(
                                                        modifier = Modifier.padding(
                                                                horizontal = 16.dp
                                                        )
                                                )
                                        }
                                }
                        }

                }


        }
}