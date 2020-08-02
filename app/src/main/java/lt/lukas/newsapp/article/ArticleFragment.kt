package lt.lukas.newsapp.article

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_article.view.*
import lt.lukas.newsapp.R

class ArticleFragment : Fragment() {

    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        loadArticle(view)
        return view
    }

    private fun loadArticle(view: View) {
        view.textViewTitle.text = args.article.title
        view.textViewDescription.text = args.article.description
        view.textViewAuthor.text = args.article.author
        view.textViewDate.text = args.article.publishedAt
        view.buttonReadFullArticle.setOnClickListener {
            val uri = Uri.parse(args.article.url)
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }
}
