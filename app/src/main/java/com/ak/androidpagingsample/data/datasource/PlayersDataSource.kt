package com.ak.androidpagingsample.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ak.androidpagingsample.data.model.PlayerItem
import com.ak.androidpagingsample.network.ApiService
import com.ak.androidpagingsample.utils.Constants.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException


class PlayersDataSource(private val apiService: ApiService) :
    PagingSource<Int, PlayerItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlayerItem> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.getPlayers(params.loadSize, page)
            val players = mutableListOf<PlayerItem>()
            players.addAll(response.data as MutableList<PlayerItem>)
            LoadResult.Page(
                data = players,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.meta?.nextPage == null) null else page + 1
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, PlayerItem>): Int? {
        return state.anchorPosition
    }
}