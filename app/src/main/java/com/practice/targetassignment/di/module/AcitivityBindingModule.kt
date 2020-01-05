package com.practice.targetassignment.di.module

import com.practice.targetassignment.ui.main.MainActivity
import com.practice.targetassignment.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity
}
