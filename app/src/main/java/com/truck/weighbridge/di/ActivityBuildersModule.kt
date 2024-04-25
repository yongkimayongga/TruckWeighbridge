package com.truck.weighbridge.di

import com.truck.weighbridge.ui.login.LoginActivity
import com.truck.weighbridge.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {

    // Method #1
    @ContributesAndroidInjector(modules = [ViewModelModule::class, FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

    // Method #2
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun contributeLoginActivity(): LoginActivity


}