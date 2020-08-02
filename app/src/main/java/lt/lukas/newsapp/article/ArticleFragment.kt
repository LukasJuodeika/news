package lt.lukas.newsapp.article

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_article.view.*
import lt.lukas.newsapp.R
import lt.lukas.newsapp.entities.Article

class ArticleFragment : Fragment() {

    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        view.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
        loadArticle(view, args.article)
        return view
    }

    override fun onResume() {
        super.onResume()
        appBarLayout.setExpanded(false)
    }

    private fun loadArticle(view: View, article: Article) {
        view.textViewTitle.text = article.title
        view.textViewDescription.text = article.description
        view.textViewAuthor.text = article.author
        view.textViewDate.text = article.publishedAt
        Glide.with(this).load(article.urlToImage).into(view.imageView)
        view.buttonReadFullArticle.setOnClickListener {
            val uri = Uri.parse(article.url)
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }
}
