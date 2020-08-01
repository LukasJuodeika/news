package lt.lukas.newsapp.articlelist

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import lt.lukas.newsapp.Mocks
import lt.lukas.newsapp.entities.Article
import lt.lukas.newsapp.repositories.NewsRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ArticleListPresenterTest {

    @Mock
    lateinit var newsRepository: NewsRepository

    @Mock
    lateinit var view: ArticleListContract.View

    lateinit var presenter: ArticleListPresenter

    val scheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ArticleListPresenter(
            newsRepository,
            view,
            scheduler,
            scheduler
        )
    }

    @Test
    fun refreshData_valid() {
        // Assemble
        val articles = listOf(Mocks.article(), Mocks.article())
        doReturn(Single.just(articles))
            .whenever(newsRepository).fetchTopHeadlines()

        // Act
        presenter.refreshData()

        // Assert
        verify(view).viewLoader()
        verify(view).hideLoader()
        verify(view).viewArticles(articles)
        verify(view, never()).viewError()
    }

    @Test
    fun refreshData_error() {
        // Assemble
        doReturn(Single.error<List<Article>>(Throwable()))
            .whenever(newsRepository).fetchTopHeadlines()

        // Act
        presenter.refreshData()

        // Assert
        verify(view).viewLoader()
        verify(view).hideLoader()
        verify(view, never()).viewArticles(any())
        verify(view).viewError()
    }

}
