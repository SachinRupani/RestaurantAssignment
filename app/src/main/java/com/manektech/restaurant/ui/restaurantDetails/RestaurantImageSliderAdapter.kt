package com.manektech.restaurant.ui.restaurantDetails

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.manektech.restaurant.R

class RestaurantImageSliderAdapter(
    private val act: Activity,
    private val listImages: List<String>?
) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return listImages?.size ?: 0
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
        container.removeView(container)
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): View {
        val view = LayoutInflater.from(act).inflate(
            R.layout.row_item_image_slider,
            container,
            false
        )
        container.addView(view)
        loadImage(
            img = view.findViewById(R.id.imgRestaurant),
            strImageUrl = listImages?.get(position)
        )
        return view
    }

    /**
     * Function to load the image in the image view
     */
    private fun loadImage(img: ImageView, strImageUrl: String?) {
        strImageUrl?.let { strImageUrlNotNull ->
            Glide.with(img)
                .load(strImageUrlNotNull)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder)
                .into(img)
        }
    }

}