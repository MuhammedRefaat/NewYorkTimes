package com.mango.nytimes.misc

import android.content.Context
import android.widget.Toast
import com.mango.nytimes.R
import com.mango.nytimes.models.SingleNewsItem
import okhttp3.ResponseBody
import org.json.JSONObject
import java.lang.Exception

class Utils {

    companion object {

        fun getThumbUrl(newsItemDetails: SingleNewsItem): String? {
            return try {
                newsItemDetails.media[0].mediaMetadata[0].url
            } catch (e: Exception) {
                null
            }
        }

        fun getImageUrl(newsItemDetails: SingleNewsItem): String? {
            return try {
                newsItemDetails.media[0].mediaMetadata[2].url
            } catch (e: Exception) {
                null
            }
        }

        fun displayError(context: Context, errorBody: ResponseBody?) {
            if (errorBody != null) {
                try {
                    val jObjError = JSONObject(errorBody.string())
                    Toast.makeText(
                        context, jObjError.getString("message"),
                        Toast.LENGTH_LONG
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        context.resources.getText(R.string.something_went_wrong),
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {
                Toast.makeText(
                    context, context.resources.getText(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

}