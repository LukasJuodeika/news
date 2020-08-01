package lt.lukas.newsapp.repositories

import io.reactivex.Single
import lt.lukas.newsapp.entities.Article

interface NewsRepository {

    fun fetchTopHeadlines(): Single<List<Article>>
}