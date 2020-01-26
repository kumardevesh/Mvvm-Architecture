package com.practice.targetassignment.di.module

import android.app.Application
import android.content.Context
import com.practice.targetassignment.di.util.RxSingleSchedulers
import com.practice.targetassignment.repository.RepoService
import com.practice.targetassignment.util.SharedPrefUtil
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Module(includes = [ViewModelModule::class])
class ApplicationModule {

    private val BASEURL = "https://github-trending-api.now.sh/"


    @Provides
    @Singleton
    internal fun provideAppContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASEURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofitService(retrofit: Retrofit): RepoService {
        return retrofit.create<RepoService>(RepoService::class.java)
    }

    @Provides
    fun providesScheduler(): RxSingleSchedulers {
        return RxSingleSchedulers.DEFAULT
    }

    @Provides
    @Singleton
    internal fun providePrefManager(context: Context): SharedPrefUtil {
        return SharedPrefUtil(context)
    }

}