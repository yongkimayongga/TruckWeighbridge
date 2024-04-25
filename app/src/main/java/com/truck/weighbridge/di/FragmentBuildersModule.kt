package com.truck.weighbridge.di


import com.truck.weighbridge.ui.list.AddFragment
import com.truck.weighbridge.ui.list.EditFragment
import com.truck.weighbridge.ui.list.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuildersModule {

    // Method #1
    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ListFragment

    // Method #2
    @ContributesAndroidInjector
    abstract fun contributeAddFragment(): AddFragment

    // Method #3
    @ContributesAndroidInjector
    abstract fun contributeEditFragment(): EditFragment
}