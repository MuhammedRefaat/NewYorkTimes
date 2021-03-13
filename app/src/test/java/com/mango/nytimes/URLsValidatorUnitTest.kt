package com.mango.nytimes

import com.mango.nytimes.misc.AppConstants
import org.junit.Test
import org.junit.Assert.*
import java.net.URI
import java.net.URL

class URLsValidatorUnitTest {

    @Test
    fun getNewsDetailsCallUrlValidator_ReturnsTrue() {
        assertTrue(
            URL(AppConstants.APIS_URL + AppConstants.APIS_URL).toURI()
                    is URI
        )
    }

}