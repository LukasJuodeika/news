package lt.lukas.newsapp.di.modules

import dagger.Module
import dagger.Provides
import lt.lukas.newsapp.network.NewsService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class
    ]
)
class ServiceModule {

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }
}
