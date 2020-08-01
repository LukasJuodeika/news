package lt.lukas.newsapp.network

import io.reactivex.Single
import lt.lukas.newsapp.network.entities.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsService {

    @GET("http://newsapi.org/v2/top-headlines?sortBy=publishedAt&country=us&apiKey=57a79eac5a8f44efa2bd3408139b83f3")
    @Headers("Content-Type: application/json")
    fun getTopHeadlines(): Single<NewsResponse>
}
