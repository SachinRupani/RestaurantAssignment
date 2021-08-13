package com.manektech.restaurant.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Restaurant List API Main Response Container
 */
@JsonClass(generateAdapter = true)
data class RestaurantListApiResponseContainer(

    val status: Int?, //Success: 200

    @Json(name = "data")
    val listRestaurantApiModel: List<RestaurantApiModel>?
) {
    /**
     * Property to check if the response was successful
     */
    val isSuccess: Boolean
        get() = status in 200..299
}

/**
 * Main Restaurant Model coming from API
 */
@JsonClass(generateAdapter = true)
data class RestaurantApiModel(
    val id: Int?,
    val title: String,
    val description: String?,
    val rating: Int?,
    val address: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    @Json(name = "phone_no") val phoneNo: String,
    @Json(name = "long") val longitude: Double?,
    @Json(name = "lat") val latitude: Double?,
    @Json(name = "created_at") val createdAt: String?,
    @Json(name = "updated_at") val updatedAt: String?,
    @Json(name = "img") val listImages: List<ImageModel>?
)

/**
 * Image Model inside Restaurant Model
 * coming from API
 */
@JsonClass(generateAdapter = true)
data class ImageModel(
    val id: Int?,
    @Json(name = "main_id") val mainId: Int?,
    @Json(name = "image") val imageUrl: String?
)