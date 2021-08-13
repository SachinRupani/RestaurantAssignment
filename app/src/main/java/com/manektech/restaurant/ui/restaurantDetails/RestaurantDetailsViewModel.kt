package com.manektech.restaurant.ui.restaurantDetails

import androidx.lifecycle.*
import com.manektech.restaurant.MyApplication
import com.manektech.restaurant.data.local.entities.RestaurantLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Main ViewModel
class RestaurantDetailsViewModel(private val application: MyApplication):ViewModel() {
    /**
     * LiveData of restaurant information
     */
    private val _restaurantInfo = MutableLiveData<RestaurantLocalModel?>()
    val restaurantInfo: LiveData<RestaurantLocalModel?>
        get() = _restaurantInfo

    /**
     * Function to fetch the restaurant details
     * from the local db table
     */
    fun fetchRestaurantDetails(restaurantId:Int?){
        restaurantId?.let { restaurantIdNotNull ->
            viewModelScope.launch(Dispatchers.IO) {
                val restaurantLocalModel = application.restaurantRepository.getLocalRestaurantInfo(id = restaurantIdNotNull)
                _restaurantInfo.postValue(restaurantLocalModel)
            }
        }
    }
}

//ViewModel Factory
class RestaurantDetailsViewModelFactory(
    private val application: MyApplication
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantDetailsViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}