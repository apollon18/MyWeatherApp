package demo.app

import androidx.multidex.MultiDexApplication
import demo.app.helpers.RealmHelper


open class MyWeatherApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        RealmHelper.initRealmDatabase(applicationContext)
    }
}