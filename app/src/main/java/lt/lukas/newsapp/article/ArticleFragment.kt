package lt.lukas.newsapp.article

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
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
        view.toolbar.setNavigationOnClickListener { activity?.onBackPressed()}
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
                startEnterTransitionAfterLoadingImage(article.urlToImage, this)
            }
            buttonReadFullArticle.setOnClickListener {
                viewOnChrome(article.url)
            }
        }
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

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }

    private fun startEnterTransitionAfterLoadingImage(
        imageAddress: String,
        imageView: ImageView
    ) {
        Glide.with(this)
            .load(imageAddress)
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(imageView)
    }

}
