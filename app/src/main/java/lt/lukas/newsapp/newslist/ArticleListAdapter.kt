package lt.lukas.newsapp.newslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.article_list_item.view.*
import lt.lukas.newsapp.R
import lt.lukas.newsapp.entities.Article


class ArticleListAdapter(
    private val context: Context,
    private var articles: List<Article>?,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.article_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(articles!![position])
    }

    override fun getItemCount(): Int {
        return articles!!.size
    }

    fun updateList(list: List<Article>) {
        articles = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(article: Article) {
            Glide.with(context).load(article.urlToImage).into(itemView.imageViewThumbnail)
            itemView.textViewTitle.text = article.title
            itemView.textViewDate.text = article.publishedAt
            itemView.setOnClickListener {
                listener.onItemClick(article)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }
}
