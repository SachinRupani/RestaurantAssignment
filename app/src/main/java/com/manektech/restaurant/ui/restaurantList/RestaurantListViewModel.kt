package com.manektech.restaurant.ui.restaurantList

import androidx.lifecycle.*
import com.manektech.restaurant.MyApplication
import com.manektech.restaurant.R
import com.manektech.restaurant.data.local.entities.RestaurantLocalModel
import com.manektech.restaurant.data.toLocalModel
import com.manektech.restaurant.utils.isNetworkOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

//Main ViewModel
class RestaurantListViewModel(private val application: MyApplication) : ViewModel() {

    /**
     * LiveData of restaurant list
     */
    private val _listRestaurants = MutableLiveData<List<RestaurantLocalModel>?>()
    val listRestaurants: LiveData<List<RestaurantLocalModel>?>
        get() = _listRestaurants

    /**
     * LiveData isLoading
     * true => Currently fetching from API or Local DB
     * false => Fetching completed/failed
     */
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    /**
     * Error message causing due to API error
     * or Internet Error or some local DB error
     */
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    /**
     * Function which will fetch the list of restaurants
     * First check if restaurants list is present in local db,
     * if list is not present in local, then we will fetch from API first
     * then store it in local
     */
    fun fetchRestaurantList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                //Show Loading indication through isLoading Live Data
                _isLoading.postValue(true)

                val repository = application.restaurantRepository
                var listLocalStoredRestaurants = repository.getAllLocalRestaurants()

                if (listLocalStoredRestaurants == null || listLocalStoredRestaurants.isEmpty()) {
                    /**
                     * Restaurants not present in local DB table
                     * so fetch from API
                     */
                    if (application.isNetworkOnline()) {
                        repository.apiCallGetRestaurantList()?.forEach { restaurantApiModel ->
                            repository.insertLocalRestaurant(restaurantApiModel.toLocalModel())
                        }
                        delay(400)
                        listLocalStoredRestaurants = repository.getAllLocalRestaurants()
                    } else {
                        emitErrorMessage(errorStringResourceId = R.string.error_internet_connection)
                    }
                }

                /**
                 * Once restaurant list is stored on local
                 * now post the list to live data
                 */
                _listRestaurants.postValue(listLocalStoredRestaurants)

                //Hide Loading indication (isLoading Live Data)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                Timber.e("fetchRestaurantList Exception $e")
                //Hide Loading indication (isLoading Live Data)
                _isLoading.postValue(false)

                //Emit the error message as exception occurred
                emitErrorMessage(R.string.oops_something_went_wrong)
            }
        }
    }

    /**
     * Function to emit the error message causing due to
     * API or Internet or Local DB
     */
    private fun emitErrorMessage(errorStringResourceId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _errorMessage.postValue(application.getString(errorStringResourceId))
        }
    }

}

//ViewModel Factory
class RestaurantListViewModelFactory(
    private val application: MyApplication
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantListViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

