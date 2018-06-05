package org.davidd.menu

import android.app.Application
import android.content.Context
import android.content.Intent
import org.davidd.menu.data.api.OrdersFetcherService

// Not object class. AndroidManifest.xml error happen.
class MainApplication : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        val intent = Intent(this, OrdersFetcherService::class.java)
        startService(intent)
    }

    companion object {
        private var instance: MainApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}