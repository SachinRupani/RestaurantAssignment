package com.manektech.restaurant.ui.restaurantList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.manektech.restaurant.R
import com.manektech.restaurant.data.local.entities.RestaurantLocalModel
import com.manektech.restaurant.databinding.RowItemRestaurantListBinding
import timber.log.Timber

class RestaurantListAdapter(private val onRestaurantItemClick: (restaurant: RestaurantLocalModel) -> Unit) :
    RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    /**
     * The audio files that our adapter will show
     */
    private var arrayListRestaurant = ArrayList<RestaurantLocalModel>()

    fun setData(listRestaurantUpdated: List<RestaurantLocalModel>) {
        Timber.d("setData Called -> $listRestaurantUpdated")
        val diffCallback = RestaurantDiffCallback(arrayListRestaurant, listRestaurantUpdated)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        arrayListRestaurant.clear()
        arrayListRestaurant.addAll(listRestaurantUpdated)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun getItemCount(): Int = arrayListRestaurant.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(arrayListRestaurant[position], onRestaurantItemClick)
    }

    class ViewHolder private constructor(private val viewDataBinding: RowItemRestaurantListBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val dataBindingView: RowItemRestaurantListBinding = DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.row_item_restaurant_list,
                    parent,
                    false
                )
                return ViewHolder(dataBindingView)
            }
        }

        fun bindData(model: RestaurantLocalModel, itemClick: (model: RestaurantLocalModel) -> Unit) {
            viewDataBinding.model = model
            viewDataBinding.cnsLayoutItemParent.setOnClickListener { itemClick(model) }
            viewDataBinding.executePendingBindings()
        }
    }

    class RestaurantDiffCallback(
        private val oldList: List<RestaurantLocalModel>,
        private val newList: List<RestaurantLocalModel>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id === newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val (_, oldTitle) = oldList[oldPosition]
            val (_, newTitle) = newList[newPosition]

            return oldTitle == newTitle
        }

        @Nullable
        override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
            return super.getChangePayload(oldPosition, newPosition)
        }
    }
}