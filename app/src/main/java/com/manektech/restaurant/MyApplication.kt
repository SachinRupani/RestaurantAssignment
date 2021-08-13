package com.manektech.restaurant

import android.app.Application
import com.manektech.restaurant.data.local.RestaurantDatabase
import com.manektech.restaurant.data.repository.RestaurantRepository
import timber.log.Timber

class MyApplication : Application() {
    /**
     * Using by lazy so the database and the repository are only created when they're needed
     * rather than when the application starts
     */
    private val database by lazy { RestaurantDatabase.getDatabase(this) }
    val restaurantRepository by lazy { RestaurantRepository(database.restaurantDao()) }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}