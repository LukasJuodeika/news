package lt.lukas.newsapp.repositories

import io.reactivex.Single
import lt.lukas.newsapp.entities.Article
import lt.lukas.newsapp.network.NewsService
import lt.lukas.newsapp.transformers.NewsResponseTransformer

class NewsRepositoryExternal(
    private val newsService: NewsService
) : NewsRepository {

    override fun fetchTopHeadlines(): Single<List<Article>> {
        return newsService.getTopHeadlines().map {
            NewsResponseTransformer.responseToArticles(it)
        }
    }
}
