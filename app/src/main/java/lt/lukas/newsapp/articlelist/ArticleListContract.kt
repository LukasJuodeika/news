package lt.lukas.newsapp.articlelist

import lt.lukas.newsapp.entities.Article

interface ArticleListContract {

    interface View {
        fun viewArticles(list: List<Article>)

        fun viewError()

        fun viewNoInternet()

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