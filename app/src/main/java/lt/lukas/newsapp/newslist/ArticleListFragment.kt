package lt.lukas.newsapp.newslist

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

class ArticleListFragment : Fragment(), ArticleListAdapter.OnItemClickListener {

    private lateinit var adapter: ArticleListAdapter

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
        return view
    }

    private fun setupRecyclerView(view: View) {
        view.recycler.layoutManager = LinearLayoutManager(activity)
        adapter = ArticleListAdapter(requireContext(), emptyList<Article>(), this)
        view.recycler.adapter = adapter
        newsRepository.fetchTopHeadlines()//temp implementation
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.updateList(it)
            }, {})
    }

    override fun onItemClick(article: Article) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
