package com.truck.weighbridge.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Truck::class, LoginToken::class], version = 1, exportSchema = false)
abstract class TruckDatabase : RoomDatabase() {
    abstract fun truckDao(): TruckDao

    abstract fun loginDao(): LoginDao
}