package com.jxareas.xpensor.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.jxareas.xpensor.core.data.local.preferences.DefaultUserPreferences
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun provideDefaultUserPreferences(sharedPreferences: SharedPreferences): UserPreferences =
        DefaultUserPreferences(sharedPreferences)
}
