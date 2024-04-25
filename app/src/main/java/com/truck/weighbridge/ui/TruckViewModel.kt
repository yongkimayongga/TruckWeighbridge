package com.truck.weighbridge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.truck.weighbridge.persistence.Truck
import com.truck.weighbridge.repository.TruckRepository
import javax.inject.Inject

class TruckViewModel @Inject constructor(
    val truckRepository: TruckRepository
) : ViewModel() {

    // Method #1
    fun insert(truck: Truck) {
        truckRepository.insert(truck)
    }

    // Method #2
    fun delete(truck: Truck) {
        truckRepository.delete(truck)
    }

    // Method #3
    fun deleteById(id: Int) {
        truckRepository.deleteById(id)
    }

    // Method #4
    fun update(truck: Truck) {
        truckRepository.update(truck)
    }

    // Method #5
    fun getAllTrucks(): LiveData<List<Truck>> {
        return truckRepository.getAllTrucks()
    }

    companion object {
        const val PATH_NAME = "driverName"
        const val PATH_DATE = "date"
        const val PATH_LICENSE = "licenseNumber"
        const val ASC = 1
        const val DESC = 2
    }

}