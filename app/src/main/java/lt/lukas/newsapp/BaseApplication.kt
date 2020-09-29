package lt.lukas.newsapp

import android.app.Application
import android.content.Context
import lt.lukas.newsapp.di.components.AppComponent
import lt.lukas.newsapp.di.components.DaggerAppComponent
import timber.log.Timber

class BaseApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }

    fun appComponent(): AppComponent {
        return appComponent
    }
}

fun Context.appComponent(): AppComponent {
    return (this.applicationContext as BaseApplication).appComponent()
}
