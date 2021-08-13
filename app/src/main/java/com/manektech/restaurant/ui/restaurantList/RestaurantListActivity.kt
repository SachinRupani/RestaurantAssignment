package com.manektech.restaurant.ui.restaurantList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.manektech.restaurant.MyApplication
import com.manektech.restaurant.R
import com.manektech.restaurant.data.local.entities.RestaurantLocalModel
import com.manektech.restaurant.databinding.ActivityRestaurantListBinding
import com.manektech.restaurant.ui.restaurantDetails.RestaurantDetailsActivity
import com.manektech.restaurant.utils.showRedErrorSnackBar

class RestaurantListActivity : AppCompatActivity() {

    private var binding: ActivityRestaurantListBinding? = null
    private var viewModel: RestaurantListViewModel? = null

    private var restaurantListAdapter: RestaurantListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize data binding object (binding)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_list)
        binding?.lifecycleOwner = this

        setupRestaurantListAdapter()
        initViewModel()
        attachObservers()
        fetchRestaurantList()
        handleClicks()
    }

    /**
     * Initialize ViewMode and ViewModelFactory
     */
    private fun initViewModel() {
        val viewModelFactory =
            RestaurantListViewModelFactory(application = application as MyApplication)
        viewModel = ViewModelProvider(this, viewModelFactory)[RestaurantListViewModel::class.java]
    }

    /**
     * Function which contains all the live data observers
     * and the observe operation is done
     */
    private fun attachObservers() {
        //Observe list of restaurants
        viewModel?.listRestaurants?.observe(this, { listRestaurants ->
            listRestaurants?.apply {
                //Update the data into the adapter
                restaurantListAdapter?.setData(listRestaurantUpdated = this)
            }
        })

        //Observe error message
        viewModel?.errorMessage?.observe(this, { strErrMsg ->
            strErrMsg?.apply {
                //Show error in snack bar
                binding?.cnsLayoutParent?.showRedErrorSnackBar(strErrMsg = this)
            }
        })

        //Observe isLoading status
        viewModel?.isLoading?.observe(this, { isLoading ->
            //Show/Hide progress indicator based on isLoading Value
            binding?.progressLoadingIndicator?.isVisible = isLoading == true
        })
    }

    /**
     * Function to fetch the restaurants
     * through viewModel
     */
    private fun fetchRestaurantList() {
        viewModel?.fetchRestaurantList()
    }

    /**
     * Function to set the list of restaurant
     * into the recycler view
     */
    private fun setupRestaurantListAdapter() {
        if (restaurantListAdapter == null) {
            restaurantListAdapter = RestaurantListAdapter(this::onRestaurantItemClick)
            binding?.rvRestaurantList?.setHasFixedSize(true)
            binding?.rvRestaurantList?.adapter = restaurantListAdapter
        }
    }

    /**
     * Function called on click of the item in the restaurant list
     */
    private fun onRestaurantItemClick(restaurantModel: RestaurantLocalModel) {
        //Navigate to Restaurant Details Page
        val intentDetails = Intent(applicationContext, RestaurantDetailsActivity::class.java).also {
            it.putExtra(RestaurantDetailsActivity.KEY_RESTAURANT_ID, restaurantModel.id)
        }
        startActivity(intentDetails)
    }

    /**
     * Function which has all the views click listeners
     */
    private fun handleClicks() {
        binding?.apply {
            txtBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}