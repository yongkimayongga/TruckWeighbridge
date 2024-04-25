package com.truck.weighbridge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.truck.weighbridge.databinding.TruckItemsBinding
import com.truck.weighbridge.persistence.Truck


class TruckAdapter(
    truckList: List<Truck>,
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<TruckAdapter.ViewHolder>() {

    private val trucksInitialize = mutableListOf<Truck>()
    private val trucks = mutableListOf<Truck>()
    private val DESC = 2
    private val PATH_DATE = "date"
    private val PATH_LICENSE = "licenseNumber"

    init {
        trucksInitialize.addAll(truckList)
        trucks.addAll(truckList)
    }

    // Method #1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            TruckItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, interaction)
    }

    // Method #2
    override fun getItemCount() = trucks.size

    // Method #3
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = trucks[position])
    }

    // Method #4
    fun swap(trucks: List<Truck>) {
        val diffCallback = DiffCallback(this.trucks, trucks)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.trucks.clear()
        this.trucks.addAll(trucks)
        diffResult.dispatchUpdatesTo(this)
    }

    // Method #5
    class ViewHolder(
        private val binding: TruckItemsBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        // Method #6
        fun bind(item: Truck) {
            binding.txtDriverName.text = item.driverName
            binding.txtLicenseNumber.text = item.licenseNumber
            binding.txtDate.text = item.date
            binding.txtTime.text = item.time
            binding.txtInboundTonnage.text = item.inboundTonnage
            binding.txtOutboundTonnage.text = item.outboundTonnage
            binding.txtNetWeight.text = item.netWeight

            //Handle item click
            binding.rlCardItem.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
        }

    }

    // Method #7
    interface Interaction {
        fun onItemSelected(position: Int, item: Truck)
    }

    // Method 8
    fun sort(trucks: List<Truck>, sortType: Int, sortBy: String) {
        val diffCallback = DiffCallback(this.trucks, trucks)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.trucks.clear()

        if (sortType == DESC) {
            this.trucks.addAll(trucks.sortedByDescending { getSortByValue(sortBy, it) })
        } else {
            this.trucks.addAll(trucks.sortedBy { getSortByValue(sortBy, it) })
        }
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    // Method 9
    private fun getSortByValue(sortBy: String, it: Truck): String? {
        return if (sortBy.contentEquals(PATH_LICENSE)) {
            it.licenseNumber
        } else if (sortBy.contentEquals(PATH_DATE)) {
            it.date
        } else {
            it.driverName
        }
    }

    // Method 10
    fun filter(trucks: List<Truck>, filterBy: String, filterValue: String) {
        val diffCallback = DiffCallback(this.trucks, trucks)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.trucks.clear()
        this.trucks.addAll(trucks.filter {
            getFilterByValue(
                filterBy,
                it
            ).contentEquals(filterValue)
        })

        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    // Method 11
    private fun getFilterByValue(filterBy: String, it: Truck): String? {
        return if (filterBy.contentEquals(PATH_LICENSE)) {
            it.licenseNumber
        } else if (filterBy.contentEquals(PATH_DATE)) {
            it.date
        } else {
            it.driverName
        }
    }
}