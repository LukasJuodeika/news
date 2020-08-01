package lt.lukas.newsapp.articlelist

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import lt.lukas.newsapp.entities.Article
import lt.lukas.newsapp.repositories.NewsRepository

class ArticleListPresenter(
    private val newsRepository: NewsRepository,
    private val view: ArticleListContract.View,
    private val ioScheduler: Scheduler,
    private val uiScheduler: Scheduler
) : ArticleListContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onAttach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDetach() {
        compositeDisposable.dispose()
    }

    override fun refreshData() {
        compositeDisposable.add(
            newsRepository.fetchTopHeadlines()
                .doOnSubscribe { view.viewLoader() }
                .doFinally { view.hideLoader() }
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    view.viewArticles(it)
                }, {
                    view.viewError()
                })
        )
    }

    override fun itemClick(article: Article) {
        view.openArticle(article)
    }


}
