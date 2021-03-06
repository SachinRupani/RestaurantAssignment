package com.manektech.restaurant.networking


import com.manektech.restaurant.data.remote.RestaurantListApiResponseContainer
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitClient {

    /**
     * Endpoint to fetch the list of restaurants
     */
    @GET("restaurants_list")
    suspend fun getRestaurantList(): Response<RestaurantListApiResponseContainer>

}