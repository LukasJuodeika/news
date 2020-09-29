package lt.lukas.newsapp.transformers

import lt.lukas.newsapp.DateFormatters
import lt.lukas.newsapp.entities.Article
import lt.lukas.newsapp.network.entities.NewsResponse

object NewsResponseTransformer {

    fun responseToArticles(response: NewsResponse): List<Article> {
        return response.articles
            .filterNotNull()
            .map { articleData ->
                Article(
                    author = articleData.author ?: "",
                    title = articleData.title ?: "",
                    description = articleData.description ?: "",
                    url = articleData.url ?: "",
                    urlToImage = articleData.urlToImage ?: "",
                    publishedAt = DateFormatters.formatNewsapiDate(articleData.publishedAt ?: "")
                )
            }
            .toList()
    }

}
