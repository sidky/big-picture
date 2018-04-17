package com.github.sidky.bigpicture.xml

import android.util.Xml
import com.github.sidky.bigpicture.model.Rss
import okhttp3.Response
import org.koin.Koin
import org.xmlpull.v1.XmlPullParser

class FeedParser {
    fun parse(response: Response): Rss {
        val parser = createParser()
        parser.setInput(response.body()?.charStream())

        return Rss.parseAsRoot(parser)
    }

    private fun createParser(): XmlPullParser {
        val parser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        return parser
    }
}