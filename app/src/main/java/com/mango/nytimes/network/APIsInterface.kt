package com.mango.nytimes.network

import com.mango.nytimes.misc.AppConstants
import com.mango.nytimes.models.NYNewsDataModel
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created by Muhammed Refaat on 30/1/2021.
 */

interface APIsInterface {

    @GET(AppConstants.NY_NEWS_API_URL_EXT + "{sections}/{period}.json")
    fun currentNyTimesNews(
        @Path(value = "sections", encoded = true) section: String,
        @Path(value = "period", encoded = true) period: String,
        @Query("api-key") apiKey: String
    ): Single<NYNewsDataModel>

}