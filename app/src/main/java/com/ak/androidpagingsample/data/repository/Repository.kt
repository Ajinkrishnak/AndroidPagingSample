package com.ak.androidpagingsample.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ak.androidpagingsample.data.datasource.PlayersDataSource
import com.ak.androidpagingsample.data.model.PlayerItem
import com.ak.androidpagingsample.network.ApiService
import com.ak.androidpagingsample.utils.Constants.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    fun getPlayers(): Flow<PagingData<PlayerItem>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                PlayersDataSource(apiService)
            }
        ).flow
    }
}