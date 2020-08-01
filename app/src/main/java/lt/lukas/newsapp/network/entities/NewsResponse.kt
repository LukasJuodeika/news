package lt.lukas.newsapp.network.entities

class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleData?>
)
