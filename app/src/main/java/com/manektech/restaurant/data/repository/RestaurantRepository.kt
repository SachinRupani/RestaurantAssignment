package com.manektech.restaurant.data.repository

import androidx.annotation.WorkerThread
import com.manektech.restaurant.data.local.dao.RestaurantDao
import com.manektech.restaurant.data.local.entities.RestaurantLocalModel
import com.manektech.restaurant.data.remote.RestaurantApiModel
import com.manektech.restaurant.networking.Retrofit

class RestaurantRepository(private val restaurantDao: RestaurantDao) {

    /**
     * Local DB call
     * Get list of restaurants from local table restaurant_local_model
     */
    @WorkerThread
    fun getAllLocalRestaurants(): List<RestaurantLocalModel>? {
        return restaurantDao.getAllRestaurants()
    }


    /**
     * Local DB call
     * Get single restaurant info based on id
     * @param id Integer ID of the restaurant to be fetched
     */
    @WorkerThread
    fun getLocalRestaurantInfo(id: Int): RestaurantLocalModel? {
        return restaurantDao.getRestaurantInfo(id = id)
    }

    /**
     * Local DB call
     * Insert record into table restaurant_local_model
     * @param restaurantLocalModel Restaurant Model to insert
     */
    @WorkerThread
    suspend fun insertLocalRestaurant(restaurantLocalModel: RestaurantLocalModel) {
        restaurantDao.insert(restaurantLocalModel = restaurantLocalModel)
    }

    /**
     * Local DB call
     * Delete all restaurant rows
     */
    @WorkerThread
    suspend fun deleteAllLocalRestaurants() {
        restaurantDao.deleteAllRestaurants()
    }

    /**
     * API Network Call
     * To fetch the list of restaurants
     */
    suspend fun apiCallGetRestaurantList():List<RestaurantApiModel>? {
        return try {
            val responseBody = Retrofit.networking?.getRestaurantList()?.body()
            responseBody?.let { responseBodyNotNull ->
                when {
                    responseBodyNotNull.isSuccess -> {
                        responseBodyNotNull.listRestaurantApiModel
                    }
                    else -> null
                }
            }
        } catch (e: Exception) {
            null
        }
    }
}