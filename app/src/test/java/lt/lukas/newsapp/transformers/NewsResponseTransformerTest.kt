package lt.lukas.newsapp.transformers

import lt.lukas.newsapp.Mocks
import lt.lukas.newsapp.entities.Article
import lt.lukas.newsapp.network.entities.ArticleData
import lt.lukas.newsapp.network.entities.NewsResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class NewsResponseTransformerTest {

    @Test
    fun itemTransformation() {

        // Assemble
        val response = NewsResponse(
            status = "status",
            totalResults = 1,
            articles = listOf(
                Mocks.articleData()
            )
        )

        // Act
        val articles = NewsResponseTranformer.responseToArticles(response)

        // Assert
        assertThat(articles).containsOnly(
            Article(
                author = "author",
                title = "title",
                description = "description",
                url = "url",
                urlToImage = "urlToImage",
                publishedAt = "publishedAt"
            )
        )
    }

    @Test
    fun valid() {

        // Assemble
        val response = NewsResponse(
            status = "status",
            totalResults = 2,
            articles = listOf(
                Mocks.articleData(title = "article1"),
                Mocks.articleData(title = "article2")
            )
        )

        // Act
        val articles = NewsResponseTranformer.responseToArticles(response)

        // Assert
        assertThat(articles).hasSize(2)
        assertThat(articles).doesNotContainNull()
    }

    @Test
    fun filterNulls() {

        // Assemble
        val response = NewsResponse(
            status = "status",
            totalResults = 3,
            articles = listOf(
                Mocks.articleData(title = "article1"),
                Mocks.articleData(title = "article2"),
                null
            )
        )

        // Act
        val articles = NewsResponseTranformer.responseToArticles(response)

        // Assert
        assertThat(articles).hasSize(2)
        assertThat(articles).doesNotContainNull()
    }

    @Test
    fun emptyList() {

        // Assemble
        val response = NewsResponse(
            status = "status",
            totalResults = 0,
            articles = emptyList<ArticleData>()
        )

        // Act
        val articles = NewsResponseTranformer.responseToArticles(response)

        // Assert
        assertThat(articles).isEmpty()
    }

}
