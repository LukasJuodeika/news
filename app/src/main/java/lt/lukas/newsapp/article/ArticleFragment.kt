package lt.lukas.newsapp.article

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_article.view.*
import lt.lukas.newsapp.R
import lt.lukas.newsapp.appComponent
import lt.lukas.newsapp.components.UrlViewer
import lt.lukas.newsapp.entities.Article
import javax.inject.Inject

class ArticleFragment : Fragment() {

    @Inject
    lateinit var urlViewer: UrlViewer

    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        view.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
        view.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSharedElementTransitionOnEnter()
        postponeEnterTransition()

        loadArticle(view, args.article)
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
            imageView.apply {
                transitionName = article.url
                ArticleAnimations.startEnterTransitionAfterLoadingImage(
                    this@ArticleFragment,
                    article.urlToImage,
                    this
                )
            }
            buttonReadFullArticle.setOnClickListener {
                urlViewer.viewUrl(context, article.url)
            }
        }
    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }
}
