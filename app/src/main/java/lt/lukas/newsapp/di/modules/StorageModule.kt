package lt.lukas.newsapp.di.modules

import dagger.Module
import dagger.Provides
import lt.lukas.newsapp.network.NewsService
import lt.lukas.newsapp.repositories.NewsRepository
import lt.lukas.newsapp.repositories.NewsRepositoryExternal
import javax.inject.Singleton

@Module(
    includes = [
        ServiceModule::class
    ]
)
class StorageModule {

    @Provides
    @Singleton
    fun provideNewsRepository(newsService: NewsService): NewsRepository
            = NewsRepositoryExternal(newsService)
}
