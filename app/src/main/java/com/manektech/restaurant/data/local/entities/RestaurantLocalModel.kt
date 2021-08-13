package com.manektech.restaurant.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_local_model")
data class RestaurantLocalModel(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "rating") var rating: Int?,
    @ColumnInfo(name = "phone_no") var phoneNo: String?,
    @ColumnInfo(name = "address") var address: String?,
    @ColumnInfo(name = "city") var city: String?,
    @ColumnInfo(name = "state") var state: String?,
    @ColumnInfo(name = "country") var country: String?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "created_at") val createdAt: String?,
    @ColumnInfo(name = "updated_at") val updatedAt: String?,
    @ColumnInfo(name = "image") val imageUrl: String?,
    @ColumnInfo(name = "images_comma_separated") val allImagesCommaSeparated: String?
) {
    /**
     * To display we will use this property
     * for description text
     */
    val getDescriptionToDisplay: String
        get() {
            val strDescription = description?.trim() ?: ""
            return if (strDescription.isEmpty()) "N/A" else strDescription
        }

    /**
     * To display we will use this property
     * for address text
     */
    val getAddressToDisplay: String
        get() {
            val strAddress = address?.trim() ?: ""
            return if (strAddress.isEmpty()) "N/A" else strAddress
        }

    /**
     * Get List of string image urls
     * from the stored comma separated image Urls
     */
    val getListImageUrls: List<String>?
        get() = allImagesCommaSeparated?.split(",")?.map { it.trim() }
}