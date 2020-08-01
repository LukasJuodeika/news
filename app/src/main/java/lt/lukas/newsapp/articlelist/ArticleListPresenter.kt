package lt.lukas.newsapp.articlelist

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import lt.lukas.newsapp.entities.Article
import lt.lukas.newsapp.exceptions.NetworkExceptionResolver
import lt.lukas.newsapp.repositories.NewsRepository
import timber.log.Timber

class ArticleListPresenter(
    private val newsRepository: NewsRepository,
    private val networkExceptionResolver: NetworkExceptionResolver,
    private val view: ArticleListContract.View,
    private val ioScheduler: Scheduler,
    private val uiScheduler: Scheduler
) : ArticleListContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onAttach() {

    }

    override fun onDetach() {
        compositeDisposable.dispose()
    }

    override fun refreshData() {
        compositeDisposable.add(
            newsRepository.fetchTopHeadlines()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { view.viewLoader() }
                .doFinally { view.hideLoader() }
                .subscribe({
                    view.viewArticles(it)
                }, {
                    networkExceptionResolver.exception(it)
                })
        )
    }

    override fun itemClick(article: Article) {
        view.openArticle(article)
    }
}
