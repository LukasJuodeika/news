package lt.lukas.newsapp.articlelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_article_list.view.*
import lt.lukas.newsapp.R
import lt.lukas.newsapp.appComponent
import lt.lukas.newsapp.entities.Article
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
        presenter = ArticleListPresenter(
            newsRepository,
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

    override fun onItemClick(article: Article) {
        presenter.itemClick(article)
    }

    override fun viewArticles(list: List<Article>) {
        adapter.updateList(list)
    }

    override fun viewError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewNoInternet() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewLoader() {

    }

    override fun hideLoader() {

    }

    override fun openArticle(article: Article) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
