package com.truck.weighbridge.di

import androidx.lifecycle.ViewModel

import com.truck.weighbridge.ui.TruckViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    // Method #1
    @Binds
    @IntoMap
    @ViewModelKey(TruckViewModel::class)
    abstract fun bindMainViewModel(moviesViewModel: TruckViewModel): ViewModel
}
