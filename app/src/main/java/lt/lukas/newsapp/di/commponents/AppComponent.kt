package lt.lukas.newsapp.di.commponents

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import lt.lukas.newsapp.BaseApplication
import lt.lukas.newsapp.MainActivity
import lt.lukas.newsapp.di.modules.NetworkModule
import lt.lukas.newsapp.di.modules.ServiceModule
import lt.lukas.newsapp.di.modules.StorageModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        ServiceModule::class,
        StorageModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: BaseApplication)
    fun inject(mainActivity: MainActivity)
}
