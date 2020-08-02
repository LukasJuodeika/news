package lt.lukas.newsapp.article

import android.content.ActivityNotFoundException
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
import lt.lukas.newsapp.Constants
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
        view.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        loadArticle(view, args.article)
        return view
    }

    override fun onResume() {
        super.onResume()
        appBarLayout.setExpanded(false)
    }

    private fun loadArticle(view: View, article: Article) {
        view.apply {
            textViewTitle.text = article.title
            textViewDescription.text = article.description
            textViewAuthor.text = article.author
            textViewDate.text = article.publishedAt
            buttonReadFullArticle.setOnClickListener {
                viewOnChrome(article.url)
            }
        }
        Glide.with(this).load(article.urlToImage).into(view.imageView)
    }

    /**
     * Opens url on Chrome browswer. If chrome browswer is not available, uses default.
     */
    private fun viewOnChrome(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage(Constants.GOOGLE_CHROME_PACKAGE)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

}
