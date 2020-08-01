package lt.lukas.newsapp.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_news_list.view.*
import lt.lukas.newsapp.R

class NewsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        view.textView.setOnClickListener {
            findNavController().navigate(
                NewsListFragmentDirections.actionNewsListFragmentToArticleFragment()
            )
        }

        return view
    }

}
