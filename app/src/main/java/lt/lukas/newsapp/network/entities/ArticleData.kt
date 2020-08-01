package lt.lukas.newsapp.network.entities

class ArticleData(
    val source: SourceData,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
)
