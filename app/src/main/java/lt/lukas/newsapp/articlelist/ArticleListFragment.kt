package lt.lukas.newsapp.articlelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
        presenter = ArticleListPresenter(
            newsRepository,
            NetworkExceptionResolver(this),
            this,
            Schedulers.io(),
            AndroidSchedulers.mainThread()
        )
        presenter.refreshData()
        return view
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

    override fun onItemClick(article: Article) {
        presenter.itemClick(article)
    }

    override fun viewArticles(list: List<Article>) {
        adapter.updateList(list)
    }

    override fun showNoInternetError() {
        Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show() // TODO implement
    }

    override fun showUnableToFetchData() {
        Toast.makeText(context, "Unable to fetch data", Toast.LENGTH_SHORT).show() // TODO implement
    }

    override fun viewLoader() {
    }

    override fun hideLoader() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun openArticle(article: Article) {
        findNavController().navigate(
            ArticleListFragmentDirections.actionNewsListFragmentToArticleFragment(article)
        )
    }
}
