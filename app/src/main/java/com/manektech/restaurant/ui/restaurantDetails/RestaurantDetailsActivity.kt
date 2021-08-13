package com.manektech.restaurant.ui.restaurantDetails

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.manektech.restaurant.MyApplication
import com.manektech.restaurant.R
import com.manektech.restaurant.databinding.ActivityRestaurantDetailsBinding

@Suppress("DEPRECATION")
class RestaurantDetailsActivity : AppCompatActivity() {

    private var binding: ActivityRestaurantDetailsBinding? = null
    private var viewModel: RestaurantDetailsViewModel? = null

    /**
     * This will be filled from the intent extras
     */
    private var restaurantId: Int? = null

    companion object {
        const val KEY_RESTAURANT_ID = "Restaurant_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTransparentStatusBar()

        //Initialize Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_details)
        binding?.lifecycleOwner = this

        fetchExtrasIntent()
        initViewModel()
        attachObservers()
        fetchRestaurantInformation()
        handleClicks()
    }

    /**
     * All the incoming extras from the intent
     * will be assigned inside this function
     */
    private fun fetchExtrasIntent() {
        restaurantId = intent?.extras?.getInt(KEY_RESTAURANT_ID)
    }

    /**
     * Initialize ViewMode and ViewModelFactory
     */
    private fun initViewModel() {
        val viewModelFactory =
            RestaurantDetailsViewModelFactory(application = application as MyApplication)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[RestaurantDetailsViewModel::class.java]

        //Provide viewModel reference to binding object
        binding?.viewModel = viewModel
    }

    /**
     * Function which contains all the live data observers
     * and the observe operation is done
     */
    private fun attachObservers() {
        viewModel?.restaurantInfo?.observe(this, { restaurantLocalModel ->
            restaurantLocalModel?.let { restaurantLocalModelNotNull ->
                setupViewPagerAdapter(listStringImageUrls = restaurantLocalModelNotNull.getListImageUrls)
            }
        })
    }

    /**
     * Function to fetch the restaurant details
     * via viewModel
     */
    private fun fetchRestaurantInformation() {
        viewModel?.fetchRestaurantDetails(restaurantId = restaurantId)
    }

    /**
     * Function to setup the view pager adapter
     * slider of images
     */
    private fun setupViewPagerAdapter(listStringImageUrls: List<String>?) {
        binding?.apply {
            viewPagerImages.apply {
                if (adapter == null) {
                    val viewPagerAdapter = RestaurantImageSliderAdapter(
                        act = this@RestaurantDetailsActivity,
                        listImages = listStringImageUrls
                    )
                    adapter = viewPagerAdapter
                }
            }
            sliderDotsIndicator.setViewPager(viewPagerImages)
        }
    }

    /**
     * This function contains all the views click listeners
     */
    private fun handleClicks() {
        binding?.apply {
            //Back Button Click
            txtBack.setOnClickListener {
                onBackPressed()
            }

            //Phone Number Click
            txtPhoneNo.setOnClickListener {
                try {
                    //Launch Dialer
                    val intentDialer =
                        Intent(Intent.ACTION_VIEW).also {
                            it.data = Uri.parse("tel:${txtPhoneNo.text}")
                        }
                    startActivity(intentDialer)
                } catch (e: Exception) {

                }
            }
        }
    }

    /**
     * Function to make the status bar transparent
     */
    private fun setTransparentStatusBar() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

}