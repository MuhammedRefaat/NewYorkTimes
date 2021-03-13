package com.mango.nytimes

import org.junit.Test
import org.junit.Assert.*
import com.google.gson.Gson
import com.mango.nytimes.models.NYNewsDataModel

class DataValidatorUnitTest {

    companion object {
        const val sampleNewsDataResponse: String = "{\n" +
                "    \"status\": \"OK\",\n" +
                "    \"copyright\": \"Copyright (c) 2021 The New York Times Company.  All Rights Reserved.\",\n" +
                "    \"num_results\": 20,\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"uri\": \"nyt://article/2641f50f-66e3-5c74-8d67-f87738c36883\",\n" +
                "            \"url\": \"https://www.nytimes.com/2021/01/24/health/fauci-trump-covid.html\",\n" +
                "            \"id\": 100000007567897,\n" +
                "            \"asset_id\": 100000007567897,\n" +
                "            \"source\": \"New York Times\",\n" +
                "            \"published_date\": \"2021-01-24\",\n" +
                "            \"updated\": \"2021-01-30 08:16:38\",\n" +
                "            \"section\": \"Health\",\n" +
                "            \"subsection\": \"\",\n" +
                "            \"nytdsection\": \"health\",\n" +
                "            \"adx_keywords\": \"Coronavirus (2019-nCoV);Threats and Threatening Messages;Rumors and Misinformation;United States Politics and Government;your-feed-health;your-feed-science;Content Type: Personal Profile;Fauci, Anthony S;Trump, Donald J;National Institute of Allergy and Infectious Diseases\",\n" +
                "            \"column\": null,\n" +
                "            \"byline\": \"By Donald G. McNeil Jr.\",\n" +
                "            \"type\": \"Article\",\n" +
                "            \"title\": \"Fauci on What Working for Trump Was Really Like\",\n" +
                "            \"abstract\": \"From denialism to death threats, Dr. Anthony S. Fauci describes a fraught year as an adviser to President Donald J. Trump on the Covid-19 pandemic.\",\n" +
                "            \"des_facet\": [\n" +
                "                \"Coronavirus (2019-nCoV)\",\n" +
                "                \"Threats and Threatening Messages\",\n" +
                "                \"Rumors and Misinformation\",\n" +
                "                \"United States Politics and Government\",\n" +
                "                \"your-feed-health\",\n" +
                "                \"your-feed-science\",\n" +
                "                \"Content Type: Personal Profile\"\n" +
                "            ],\n" +
                "            \"org_facet\": [\n" +
                "                \"National Institute of Allergy and Infectious Diseases\"\n" +
                "            ],\n" +
                "            \"per_facet\": [\n" +
                "                \"Fauci, Anthony S\",\n" +
                "                \"Trump, Donald J\"\n" +
                "            ],\n" +
                "            \"geo_facet\": [],\n" +
                "            \"media\": [\n" +
                "                {\n" +
                "                    \"type\": \"image\",\n" +
                "                    \"subtype\": \"photo\",\n" +
                "                    \"caption\": \"Dr. Fauci delivered remarks on the coronavirus last April during a daily White House briefing, as President Donald Trump looked on.\",\n" +
                "                    \"copyright\": \"Doug Mills/The New York Times\",\n" +
                "                    \"approved_for_syndication\": 1,\n" +
                "                    \"media-metadata\": [\n" +
                "                        {\n" +
                "                            \"url\": \"https://static01.nyt.com/images/2021/01/24/us/24VIRUS-FAUCIQA2/24VIRUS-FAUCIQA2-thumbStandard-v2.jpg\",\n" +
                "                            \"format\": \"Standard Thumbnail\",\n" +
                "                            \"height\": 75,\n" +
                "                            \"width\": 75\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"url\": \"https://static01.nyt.com/images/2021/01/24/us/24VIRUS-FAUCIQA2/24VIRUS-FAUCIQA2-mediumThreeByTwo210-v2.jpg\",\n" +
                "                            \"format\": \"mediumThreeByTwo210\",\n" +
                "                            \"height\": 140,\n" +
                "                            \"width\": 210\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"url\": \"https://static01.nyt.com/images/2021/01/24/us/24VIRUS-FAUCIQA2/24VIRUS-FAUCIQA2-mediumThreeByTwo440-v2.jpg\",\n" +
                "                            \"format\": \"mediumThreeByTwo440\",\n" +
                "                            \"height\": 293,\n" +
                "                            \"width\": 440\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ],\n" +
                "            \"eta_id\": 0\n" +
                "        },\n" +
                "    ]\n" +
                "}"

    }

    @Test
    fun newsModelDataValidator_ReturnsTrue() {
        val gson = Gson()
        val newsData = gson.fromJson(sampleNewsDataResponse, NYNewsDataModel::class.java)
        assertTrue(newsData is NYNewsDataModel)
    }

}