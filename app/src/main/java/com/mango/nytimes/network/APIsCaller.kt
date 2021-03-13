package com.mango.nytimes.network

import com.mango.nytimes.misc.AppConstants
import com.mango.nytimes.models.NYNewsDataModel
import io.reactivex.Single
import javax.inject.Inject

class APIsCaller {

    @Inject
    lateinit var api: APIsInterface

    init {
        DaggerApiComponent.builder()
            .aPIsClient(APIsClient)
            .build().inject(this)
    }

    /**
     * Getting current NewYork Times news
     */
    fun getCurrentNews(sections: String, period: String): Single<NYNewsDataModel> {
        return api.currentNyTimesNews(sections, period, AppConstants.API_KEY)
    }

}