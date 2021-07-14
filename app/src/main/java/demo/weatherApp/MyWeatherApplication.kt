package demo.weatherApp

import androidx.multidex.MultiDexApplication
import demo.weatherApp.helpers.RealmHelper


open class MyWeatherApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        RealmHelper.initRealmDatabase(applicationContext)
    }
}