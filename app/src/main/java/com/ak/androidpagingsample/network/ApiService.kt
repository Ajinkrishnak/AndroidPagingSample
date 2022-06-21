package com.ak.androidpagingsample.network
import com.ak.androidpagingsample.data.model.PlayersResponse
import com.ak.androidpagingsample.utils.EndPoints
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //get players
    @GET(EndPoints.PLAYERS)
    suspend fun getPlayers(
        @Query("per_page") per_page: Int?,
        @Query("page") page: Int?,
    ): PlayersResponse

}