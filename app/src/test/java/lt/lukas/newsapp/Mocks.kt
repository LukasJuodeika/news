package lt.lukas.newsapp

import lt.lukas.newsapp.network.entities.ArticleData
import lt.lukas.newsapp.network.entities.SourceData

object Mocks {

    fun articleData(
        sourceId: String? = "sourceid",
        sourceName: String? = "sourceName",
        author: String? = "author",
        title: String? = "title",
        description: String? = "description",
        url: String? = "url",
        urlToImage: String? = "urlToImage",
        publishedAt: String? = "publishedAt"

    ) : ArticleData {
        return ArticleData(
            source = SourceData(
                id = sourceId,
                name = sourceName
            ),
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt
        )
    }
}