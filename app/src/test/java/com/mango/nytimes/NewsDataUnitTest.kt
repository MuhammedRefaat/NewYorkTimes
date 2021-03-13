package com.mango.nytimes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.mango.nytimes.models.NYNewsDataModel
import com.mango.nytimes.models.NYTimesNewsViewModel
import com.mango.nytimes.models.SingleNewsItem
import com.mango.nytimes.network.APIsCaller
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import io.reactivex.android.plugins.RxAndroidPlugins
import org.junit.Assert
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
class NewsDataUnitTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var apisCaller: APIsCaller

    @InjectMocks
    var newsViewModel = NYTimesNewsViewModel()

    private var testNewsData: Single<NYNewsDataModel>? = null


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testSuccessResults() {
        val gson = Gson()
        val newsJsonData =
            gson.fromJson(DataValidatorUnitTest.sampleNewsDataResponse, NYNewsDataModel::class.java)
        testNewsData = Single.just(newsJsonData)

        Mockito.`when`(
            apisCaller.getCurrentNews(
                "all-sections",
                "7"
            )
        )
            .thenReturn(testNewsData)

        newsViewModel.getNewsDetails(sections = "all-sections", period = "7")

        Assert.assertEquals(
            1,
            newsViewModel.newsList.keys.size
        )
        Assert.assertEquals(false, newsViewModel.loading.value)
    }

    @Test
    fun testFailResults() {
        testNewsData = Single.error(Throwable())

        Mockito.`when`(apisCaller.getCurrentNews("", "")).thenReturn(testNewsData)

        newsViewModel.getNewsDetails(sections = "", period = "")

        Assert.assertEquals(null, newsViewModel.newsData.value)
        Assert.assertEquals(false, newsViewModel.loading.value)
    }


    @Before
    fun setUpRxSettings() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

}