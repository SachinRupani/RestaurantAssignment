package com.manektech.restaurant.data

import com.manektech.restaurant.data.local.entities.RestaurantLocalModel
import com.manektech.restaurant.data.remote.ImageModel
import com.manektech.restaurant.data.remote.RestaurantApiModel

/**
 * Extension function to map the API restaurant model
 * to local restaurant model
 */
fun RestaurantApiModel.toLocalModel(): RestaurantLocalModel {
    return RestaurantLocalModel(
        id = id,
        title = title,
        description = description,
        rating = rating,
        address = address,
        city = city,
        state = state,
        country = country,
        phoneNo = phoneNo,
        latitude = latitude,
        longitude = longitude,
        createdAt = createdAt,
        updatedAt = updatedAt,
        imageUrl = if (listImages?.isNotEmpty() == true) listImages[0].imageUrl else null,
        allImagesCommaSeparated = getCommaSeparatedImageUrls(listImages = listImages)
    )
}

fun getCommaSeparatedImageUrls(listImages: List<ImageModel>?): String {
    val strCommaSeparatedImages: StringBuilder = StringBuilder("")
    listImages?.forEach {
        it.imageUrl?.let { imageUrlNotNull ->
            strCommaSeparatedImages.append(",$imageUrlNotNull")
        }
    }
    if(strCommaSeparatedImages.startsWith(",")) {
        return strCommaSeparatedImages.substring(1)
    }
    return ""
}