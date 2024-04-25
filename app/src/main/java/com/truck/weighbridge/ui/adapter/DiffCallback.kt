package com.truck.weighbridge.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.truck.weighbridge.persistence.Truck

class DiffCallback(
    private val oldList: List<Truck>,
    private val newList: List<Truck>
) : DiffUtil.Callback() {

    // Method #1
    override fun getOldListSize() = oldList.size

    // Method #2
    override fun getNewListSize() = newList.size

    // Method #3
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    // Method #4
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].driverName == newList[newItemPosition].driverName
                && oldList[oldItemPosition].licenseNumber == newList[newItemPosition].licenseNumber
                && oldList[oldItemPosition].date == newList[newItemPosition].date
                && oldList[oldItemPosition].time == newList[newItemPosition].time
                && oldList[oldItemPosition].inboundTonnage == newList[newItemPosition].inboundTonnage
                && oldList[oldItemPosition].outboundTonnage == newList[newItemPosition].outboundTonnage
                && oldList[oldItemPosition].netWeight == newList[newItemPosition].netWeight
    }
}