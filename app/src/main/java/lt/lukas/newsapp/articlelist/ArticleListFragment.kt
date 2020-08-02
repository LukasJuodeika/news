package lt.lukas.newsapp.articlelist

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.article_list_item.view.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import kotlinx.android.synthetic.main.fragment_article_list.view.*
import lt.lukas.newsapp.R
import lt.lukas.newsapp.appComponent
import lt.lukas.newsapp.entities.Article
import lt.lukas.newsapp.exceptions.NetworkExceptionResolver
import lt.lukas.newsapp.repositories.NewsRepository
import java.io.Serializable
import javax.inject.Inject


class ArticleListFragment : Fragment(), ArticleListAdapter.OnItemClickListener,
    ArticleListContract.View {

    lateinit var adapter: ArticleListAdapter

    lateinit var presenter: ArticleListContract.Presenter

    @Inject
    lateinit var newsRepository: NewsRepository

    private var layoutManagerState: Parcelable? = null

    private var articles: List<Article>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
        view.swipeRefreshLayout.setOnRefreshListener { presenter.refreshData() }
        view.buttonRetry.setOnClickListener { presenter.refreshData() }
        presenter = ArticleListPresenter(
            newsRepository,
            NetworkExceptionResolver(this),
            this,
            Schedulers.io(),
            AndroidSchedulers.mainThread()
        )
        if (articles != null) {
            viewArticles(articles!!)
        } else {
            presenter.refreshData()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_LAYOUT_MANAGER, recycler?.layoutManager?.onSaveInstanceState())
        outState.putSerializable(KEY_ARTICLES, articles as Serializable?)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        if (savedInstanceState is Bundle) {
            layoutManagerState = savedInstanceState.getParcelable<Parcelable>(KEY_LAYOUT_MANAGER)
            articles = savedInstanceState.getSerializable(KEY_ARTICLES) as List<Article>?
        }
        super.onViewStateRestored(savedInstanceState)
    }

    private fun setupRecyclerView(view: View) {
        view.recycler.layoutManager = LinearLayoutManager(activity)
        adapter = ArticleListAdapter(requireContext(), emptyList<Article>(), this)
        view.recycler.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        presenter.onAttach()
    }

    override fun onStop() {
        presenter.onDetach()
        super.onStop()
    }

    override fun onItemClick(view: View, article: Article) {
        findNavController().navigate(
            ArticleListFragmentDirections.actionNewsListFragmentToArticleFragment(article),
            FragmentNavigatorExtras(view.imageViewThumbnail to article.url)
        )
    }

    override fun viewArticles(list: List<Article>) {
        articles = list
        adapter.updateList(list)
        groupError.visibility = View.GONE
        groupList.visibility = View.VISIBLE
        if (layoutManagerState != null) {
            (recycler as RecyclerView).layoutManager?.onRestoreInstanceState(layoutManagerState)
            layoutManagerState = null
        }
    }

    override fun showNoInternetError() {
        showError(getString(R.string.no_internet))
    }

    override fun showUnableToFetchData() {
        showError(getString(R.string.server_error))
    }

    override fun viewLoader() {
        groupError.visibility = View.GONE
        groupLoader.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        swipeRefreshLayout.isRefreshing = false
        groupLoader.visibility = View.GONE
    }

    private fun showError(message: String) {
        textViewError.text = message
        groupError.visibility = View.VISIBLE
        groupList.visibility = View.GONE
    }

    companion object {
        private const val KEY_ARTICLES = "articles"
        private const val KEY_LAYOUT_MANAGER = "layout_manager"
    }
}
