package com.truck.weighbridge.repository

import androidx.lifecycle.LiveData
import com.truck.weighbridge.persistence.Truck
import com.truck.weighbridge.persistence.TruckDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TruckRepository @Inject constructor(val truckDao: TruckDao) {

    // Method #1
    fun insert(truck: Truck) {
        CoroutineScope(Dispatchers.IO).launch {
            truckDao.insert(truck)
        }
    }

    // Method #2
    fun delete(truck: Truck) {
        CoroutineScope(Dispatchers.IO).launch {
            truckDao.delete(truck)
        }
    }

    // Method #3
    fun deleteById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            truckDao.deleteById(id)
        }
    }

    // Method #4
    fun update(truck: Truck) {
        CoroutineScope(Dispatchers.IO).launch {
            truckDao.update(truck)
        }
    }

    // Method #5
    fun getAllTrucks(): LiveData<List<Truck>> {
        return truckDao.getAllTrucks()
    }

}