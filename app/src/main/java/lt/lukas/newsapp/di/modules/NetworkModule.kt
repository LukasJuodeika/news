package lt.lukas.newsapp.di.modules

import dagger.Module
import dagger.Provides
import lt.lukas.newsapp.BuildConfig
import lt.lukas.newsapp.network.InterceptorAuth
import lt.lukas.newsapp.network.RetrofitFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideClientBuilder(): OkHttpClient.Builder {
        return RetrofitFactory
            .createUnauthorizedClientBuilder()
            .addInterceptor(InterceptorAuth(BuildConfig.API_KEY))
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(clientBuilder: OkHttpClient.Builder): OkHttpClient {
        return clientBuilder
            .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return RetrofitFactory.createRetrofit(okHttpClient, BuildConfig.BASE_URL)
    }

}
