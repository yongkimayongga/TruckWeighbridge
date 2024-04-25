package com.truck.weighbridge.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.database.FirebaseDatabase
import com.truck.weighbridge.persistence.LoginDao
import com.truck.weighbridge.persistence.TruckDao
import com.truck.weighbridge.persistence.TruckDatabase
import com.truck.weighbridge.repository.TruckRepository
import com.truck.weighbridge.session.SessionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    // Method #1
    @Singleton
    @Provides
    fun providesAppDatabase(app: Application): TruckDatabase {
        return Room.databaseBuilder(app, TruckDatabase::class.java, "truck_database")
            .allowMainThreadQueries()
            .build()
    }

    // Method #2
    @Singleton
    @Provides
    fun providesTruckDao(db: TruckDatabase): TruckDao {
        return db.truckDao()
    }

    // Method #3
    @Provides
    fun providesRepository(truckDao: TruckDao): TruckRepository {
        return TruckRepository(truckDao)
    }

    // Method #4
    @Singleton
    @Provides
    fun providesFirebaseDatabse(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    // Method #5
    @Provides
    fun providesSessionManager(loginDao: LoginDao): SessionManager {
        return SessionManager(loginDao)
    }

    // Method #6
    @Singleton
    @Provides
    fun providesLoginDao(db: TruckDatabase): LoginDao {
        return db.loginDao()
    }
}