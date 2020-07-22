package com.wayne.taiwan_s_environment

import android.app.Application
import com.wayne.taiwan_s_environment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    companion object {
        private lateinit var self : Application
        fun applicationContext(): Application {
            return self
        }
    }

    init {
        self = this
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        val modules = listOf(
            appModule
        )

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(modules)
        }
    }
}