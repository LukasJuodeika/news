package lt.lukas.newsapp.articlelist

import lt.lukas.newsapp.entities.Article
import lt.lukas.newsapp.exceptions.ErrorDisplay

interface ArticleListContract {

    interface View : ErrorDisplay {
        fun viewArticles(list: List<Article>)

        fun viewLoader()

        fun hideLoader()
    }

    interface Presenter {
        fun onAttach()

        fun onDetach()

        fun refreshData()
    }
}
