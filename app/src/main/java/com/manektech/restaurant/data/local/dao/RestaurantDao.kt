package com.manektech.restaurant.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manektech.restaurant.data.local.entities.RestaurantLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurant_local_model")
    fun getAllRestaurants(): List<RestaurantLocalModel>?

    @Query("SELECT * FROM restaurant_local_model WHERE id=:id")
    fun getRestaurantInfo(id: Int): RestaurantLocalModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(restaurantLocalModel: RestaurantLocalModel)

    @Query("DELETE FROM restaurant_local_model")
    suspend fun deleteAllRestaurants()
}