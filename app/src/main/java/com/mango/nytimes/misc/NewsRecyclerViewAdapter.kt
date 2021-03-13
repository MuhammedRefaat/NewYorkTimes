package com.mango.nytimes.misc

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mango.nytimes.R
import com.mango.nytimes.inflate
import com.mango.nytimes.models.SingleNewsItem
import com.mango.nytimes.views.activities.NewsDetailsActivity
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_news_item.view.*
import java.lang.Exception

class NewsRecyclerViewAdapter(private val news: List<SingleNewsItem>) :
    RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemHolder>() {

    class NewsItemHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var newsItemDetails: SingleNewsItem? = null

        init {
            v.setOnClickListener(this)
        }

        fun bindNews(newsItemDetails: SingleNewsItem) {
            this.newsItemDetails = newsItemDetails
            view.itemDescription.text = newsItemDetails.abstract
            view.itemDate.text = newsItemDetails.published_date
            try {
                val thumb = Utils.getThumbUrl(newsItemDetails)
                if(thumb!= null) {
                    Picasso.get().load(thumb)
                        .placeholder(R.drawable.logo)
                        .into(view.itemImage)
                }
            } catch (e: Exception) {
                print(e.toString())
            }
            try {
                view.itemSection.text = newsItemDetails.section
            } catch (e: Exception) {
                print(e.toString())
            }
            try {
                view.author.text = newsItemDetails.byline
            } catch (e: Exception) {
                print(e.toString())
            }
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
            val context = itemView.context
            val showPhotoIntent = Intent(context, NewsDetailsActivity::class.java)
            showPhotoIntent.putExtra(NEWS_ITEM, newsItemDetails)
            context.startActivity(showPhotoIntent)
        }

    }

    companion object {
        const val NEWS_ITEM = "NewsItem"
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsRecyclerViewAdapter.NewsItemHolder {
        val inflatedView = parent.inflate(R.layout.single_news_item, false)
        return NewsItemHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: NewsRecyclerViewAdapter.NewsItemHolder, position: Int) {
        val newsItem = news[position]
        holder.bindNews(newsItem)
    }

    override fun getItemCount(): Int {
        return news.size
    }

}

