package com.shenawynkov.swapcardtest.di

import android.content.Context
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.SharedPreferences
import com.shenawynkov.data.sharedPref.PrefManger
import dagger.Module
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        val MY_PREFS_NAME = "fav"
        return context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providePrefManger(prefs: SharedPreferences?): PrefManger {
        return PrefManger(prefs!!)
    }
}