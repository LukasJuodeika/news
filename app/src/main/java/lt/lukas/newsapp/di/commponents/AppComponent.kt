package lt.lukas.newsapp.di.commponents

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import lt.lukas.newsapp.BaseApplication
import lt.lukas.newsapp.MainActivity
import lt.lukas.newsapp.article.ArticleFragment
import lt.lukas.newsapp.di.modules.NetworkModule
import lt.lukas.newsapp.di.modules.ServiceModule
import lt.lukas.newsapp.di.modules.StorageModule
import lt.lukas.newsapp.articlelist.ArticleListFragment
import lt.lukas.newsapp.di.modules.AppModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        ServiceModule::class,
        StorageModule::class,
        AppModule::class
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
    fun inject(articleListFragment: ArticleListFragment)
    fun inject(articleFragment: ArticleFragment)
}
