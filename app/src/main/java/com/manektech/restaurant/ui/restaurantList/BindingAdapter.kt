package com.manektech.restaurant.ui.restaurantList

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.manektech.restaurant.R
import com.manektech.restaurant.data.local.entities.RestaurantLocalModel
import com.willy.ratingbar.ScaleRatingBar

@BindingAdapter("restaurantImage")
fun setRestaurantImage(img: ImageView, model: RestaurantLocalModel) {
    model.imageUrl?.let { imageUrlNotNull ->
        Glide.with(img)
            .load(imageUrlNotNull)
            .override(180)
            .centerCrop()
            .placeholder(R.drawable.img_placeholder)
            .into(img)
    }
}

@BindingAdapter("restaurantRating")
fun setRestaurantRating(ratingBar:ScaleRatingBar,model: RestaurantLocalModel) {
    val rating = model.rating ?: 0
    ratingBar.rating = rating.toFloat()
}

@BindingAdapter("restaurantRatingDetails")
fun setRestaurantRatingDetails(ratingBar:ScaleRatingBar,liveDataRestaurantModel:LiveData<RestaurantLocalModel>) {
    val rating = liveDataRestaurantModel.value?.rating ?: 0
    ratingBar.rating = rating.toFloat()
}

