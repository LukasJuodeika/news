package lt.lukas.newsapp.articlelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
import javax.inject.Inject

class ArticleListFragment : Fragment(), ArticleListAdapter.OnItemClickListener,
    ArticleListContract.View {

    private lateinit var adapter: ArticleListAdapter

    private lateinit var presenter: ArticleListContract.Presenter

    @Inject
    lateinit var newsRepository: NewsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article_list, container, false)
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
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.refreshData()
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
            FragmentNavigatorExtras( view.imageViewThumbnail to article.url)
        )
    }

    override fun viewArticles(list: List<Article>) {
        adapter.updateList(list)
        (groupError as Group).visibility = View.GONE
        (groupList as Group).visibility = View.VISIBLE
    }

    override fun showNoInternetError() {
        showError(getString(R.string.no_internet))
    }

    override fun showUnableToFetchData() {
        showError(getString(R.string.server_error))
    }

    override fun viewLoader() {
        (groupError as Group).visibility = View.GONE
        (groupLoader as Group).visibility = View.VISIBLE
    }

    override fun hideLoader() {
        swipeRefreshLayout.isRefreshing = false
        (groupLoader as Group).visibility = View.GONE
    }

    private fun showError(message: String) {
        textViewError.text = message
        (groupError as Group).visibility = View.VISIBLE
        (groupList as Group).visibility = View.GONE
    }
}
