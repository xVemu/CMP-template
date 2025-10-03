package pl.inno4med.asystent

import android.app.Application

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        multiplatform.network.cmptoast.AppContext.apply { set(applicationContext) }
    }
}
