package com.jxareas.xpensor.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.jxareas.xpensor.features.authentication.data.DefaultAuthenticationManager
import com.jxareas.xpensor.features.authentication.domain.manager.AuthenticationManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PreferencesModule {

    @Singleton
    @Binds
    fun bindAuthenticationManager(manager: DefaultAuthenticationManager):
            AuthenticationManager

    companion object {
        @Singleton
        @Provides
        fun providePreferences(@ApplicationContext context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
    }
}
