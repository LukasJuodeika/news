package lt.lukas.newsapp.network

import io.reactivex.Single
import lt.lukas.newsapp.network.entities.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsService {

    @GET("/v2/top-headlines?sortBy=publishedAt&country=us")
    @Headers("Content-Type: application/json")
    fun getTopHeadlines(): Single<NewsResponse>
}
