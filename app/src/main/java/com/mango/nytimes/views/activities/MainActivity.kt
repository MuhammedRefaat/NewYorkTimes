package com.mango.nytimes.views.activities

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mango.nytimes.R
import com.mango.nytimes.misc.NewsRecyclerViewAdapter
import com.mango.nytimes.misc.Utils
import com.mango.nytimes.models.NYTimesNewsViewModel
import com.mango.nytimes.models.SingleNewsItem
import okhttp3.ResponseBody
import java.math.BigInteger
import java.util.*


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: NewsRecyclerViewAdapter

    lateinit var newsViewModel: NYTimesNewsViewModel

    private lateinit var circleProgress: ProgressBar
    private lateinit var emptyScreen: ImageView
    private lateinit var newsFullDetails: Map<BigInteger, SingleNewsItem>
    private lateinit var newsList: RecyclerView
    private lateinit var periodsSpinner: Spinner
    private lateinit var sectionsSpinner: Spinner

    private val periodsDesc = arrayOf("1 day", "7 days", "30 days")
    private val periods = arrayOf("1", "7", "30")
    private var periodSelected = periods[0]

    private val sectionsDesc = arrayOf(
        "All Sections",
        "World",
        "U.S.",
        "Opinion",
        "Business",
        "Technology",
        "Arts",
        "Style",
        "Food",
        "Books"
    )
    private val sections = arrayOf(
        "all-sections",
        "world",
        "u.s.",
        "opinion",
        "business",
        "technology",
        "arts",
        "style",
        "food",
        "books"
    )
    private var sectionSelected = sections[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // declare views
        newsList = findViewById(R.id.news_list)
        circleProgress = findViewById(R.id.circular_progress)
        emptyScreen = findViewById(R.id.empty_screen_decoration)
        periodsSpinner = findViewById(R.id.period_dropdown)
        sectionsSpinner = findViewById(R.id.sections_dropdown)
        // initiating the RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        newsList.layoutManager = linearLayoutManager
        // setting the spinners
        spinnersInitialization();
        // declaring the view model and it's observer
        newsViewModel = ViewModelProvider(this).get(NYTimesNewsViewModel::class.java)
        observeNewsViewModel()
        // Get News Details
        getNewsData()
    }

    private fun spinnersInitialization() {
        // Sections spinner init
        val sectionsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this@MainActivity,
            android.R.layout.simple_spinner_item, sectionsDesc
        )
        sectionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sectionsSpinner.adapter = sectionsAdapter
        sectionsSpinner.onItemSelectedListener = this

        // Periods spinner init
        val periodsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this@MainActivity,
            android.R.layout.simple_spinner_item, periodsDesc
        )
        periodsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        periodsSpinner.adapter = periodsAdapter
        periodsSpinner.onItemSelectedListener = this
    }

    /**
     * View model observers subscribers for data handling
     */
    private fun observeNewsViewModel() {
        // observer for the loading process
        newsViewModel.loading.observe(this, androidx.lifecycle.Observer {
            if (it) {
                circleProgress.visibility = View.VISIBLE
            } else {
                circleProgress.visibility = View.GONE
            }
        })
        // observer for the data success result
        newsViewModel.newsDataError.observe(this, androidx.lifecycle.Observer {
            try {
                // get the New Error Data to handle/display
                for ((newsId, newsError) in it) {
                    // now go for it
                    emptyScreen.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                print(e.toString())
            }
        })
        // observer for the data error
        newsViewModel.newsData.observe(this, androidx.lifecycle.Observer {
            try {
                // get the Data and display it
                newsFullDetails = it
                displayNewsData()
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (it != null) {
                    Toast.makeText(
                        this@MainActivity, resources.getText(R.string.something_went_wrong),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun displayNewsData() {
        adapter = NewsRecyclerViewAdapter(newsFullDetails.values.toList())
        newsList.adapter = adapter
    }

    private fun getSections(): String {
        return sectionSelected
    }

    private fun getPeriod(): String {
        return periodSelected
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun getNewsData() {
        // call the API
        newsViewModel.getNewsDetails(getSections(), getPeriod())
    }

    fun goBack(view: View) {
        super.onBackPressed()
    }

    fun reloadProcess(view: View) {
        // give press feeling
        view.alpha = 0.3f
        Handler().postDelayed({
            view.alpha = 1.0f
        }, 150)
        // do the work
        getNewsData()
    }

    private fun goForError(errorBody: ResponseBody?) {
        Utils.displayError(this@MainActivity, errorBody)
        emptyScreen.visibility = View.VISIBLE
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent == sectionsSpinner) {
            sectionSelected = sections[position]
        } else if (parent == periodsSpinner) {
            periodSelected = periods[position]
        }
        circleProgress.visibility = View.VISIBLE
        getNewsData()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Nothing special to handle here, the default one is set to "1"
    }
}