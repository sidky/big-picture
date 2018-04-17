package com.github.sidky.bigpicture

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.sidky.bigpicture.common.Constants
import com.github.sidky.bigpicture.network.FeedHelper
import com.github.sidky.bigpicture.network.NetworkHelper
import com.github.sidky.bigpicture.xml.FeedParser
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    val network: FeedHelper by inject()
    val url: String by inject(Constants.RSS_URL)
    val feedParser: FeedParser by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        network.loadRss().subscribe { next ->
            Timber.e("Loaded: ${next}")

            next.channel?.items?.forEach {
                Timber.e("Image: ${it?.image()}")
            }
        }
    }
}
