package lt.lukas.newsapp.di.modules

import dagger.Module
import dagger.Provides
import lt.lukas.newsapp.components.UrlViewer
import lt.lukas.newsapp.components.UrlViewerChrome
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideUrlViewer(): UrlViewer {
        return UrlViewerChrome()
    }
}
