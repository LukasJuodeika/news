package lt.lukas.newsapp.articlelist

import lt.lukas.newsapp.entities.Article
import lt.lukas.newsapp.exceptions.ErrorDisplay

interface ArticleListContract {

    interface View : ErrorDisplay {
        fun viewArticles(list: List<Article>)

        fun viewLoader()

        fun hideLoader()

        fun openArticle(article: Article)
    }

    interface Presenter {
        fun onAttach()

        fun onDetach()

        fun refreshData()

        fun itemClick(article: Article)
    }
}
