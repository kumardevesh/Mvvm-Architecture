package com.practice.targetassignment.di.module

import com.practice.targetassignment.ui.main.RepoListActivity
import com.practice.targetassignment.ui.main.RepoListActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [RepoListActivityModule::class])
    abstract fun bindRepoListActivity(): RepoListActivity
}
