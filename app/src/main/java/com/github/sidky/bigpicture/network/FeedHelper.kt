package com.github.sidky.bigpicture.network

import com.github.sidky.bigpicture.model.Rss
import com.github.sidky.bigpicture.xml.FeedParser
import io.reactivex.Observable
import io.reactivex.Single

class FeedHelper(private val networkHelper: NetworkHelper,
                 private val feedParser: FeedParser,
                 private val rssUrl: String) {

    fun loadRss(): Single<Rss> =
            networkHelper
                    .load(rssUrl)
                    .map { feedParser.parse(it) }
}