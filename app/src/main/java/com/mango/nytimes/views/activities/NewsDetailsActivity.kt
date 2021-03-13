package com.mango.nytimes.views.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.mango.nytimes.R
import com.mango.nytimes.misc.NewsRecyclerViewAdapter
import com.mango.nytimes.misc.Utils
import com.mango.nytimes.models.SingleNewsItem
import com.squareup.picasso.Picasso


class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var newsDetails: SingleNewsItem

    private lateinit var mainImage: ImageView
    private lateinit var title: TextView
    private lateinit var details: TextView
    private lateinit var date: TextView
    private lateinit var by: TextView
    private lateinit var source: TextView
    private lateinit var section: TextView
    private lateinit var subSection: TextView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set the toolbar
        setContentView(R.layout.activity_news_details)
        // get news details
        newsDetails =
            intent.getSerializableExtra(NewsRecyclerViewAdapter.NEWS_ITEM) as SingleNewsItem
        // declare views
        mainImage = findViewById(R.id.mainImage)
        title = findViewById(R.id.title)
        details = findViewById(R.id.news_details)
        date = findViewById(R.id.news_date)
        by = findViewById(R.id.author)
        source = findViewById(R.id.source)
        section = findViewById(R.id.section)
        subSection = findViewById(R.id.sub_section)
        // set view display
        displayNews()
    }

    private fun displayNews() {
        // set the image
        val image = Utils.getImageUrl(newsDetails)
        if(image != null) {
            Picasso.get().load(image).into(mainImage)
        }
        // set the text areas
        title.text = newsDetails.title
        details.text = newsDetails.abstract
        date.text = newsDetails.published_date
        by.text = newsDetails.byline
        source.text = newsDetails.source
        section.text = newsDetails.section
        subSection.text = newsDetails.subsection
    }

    fun goBack(view: View) {
        super.onBackPressed()
    }

    fun share(view: View) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, newsDetails.title)
            val shareMessage =
                """
                ${newsDetails.title}
                  
                ${newsDetails.url}
                """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            //e.toString();
        }
    }

}