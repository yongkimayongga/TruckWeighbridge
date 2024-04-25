package com.truck.weighbridge.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TruckDao {

    // Method #1
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(truck: Truck): Long

    // Method #2
    @Update
    fun update(truck: Truck)

    // Method #3
    @Query("delete from tbl_truck where id = :id")
    fun deleteById(id: Int)

    // Method #4
    @Delete
    fun delete(truck: Truck)

    // Method #5
    @Query("select * from tbl_truck")
    fun getAllTrucks(): LiveData<List<Truck>>

}