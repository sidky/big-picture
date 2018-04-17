package com.github.sidky.bigpicture.model

import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

data class Item(val title: String, val link: String, val description: String, val pubDate: Date?) {

    fun image(): String? {
        val doc = Jsoup.parse(description)
        val imageElement = doc.selectFirst("img")
        return imageElement?.attr("src")
    }

    companion object {
        val TAG = "item"

        val PROP_TITLE = "title"
        val PROP_DESCRIPTION = "description"
        val PROP_LINK = "link"
        val PROP_PUB_DATE = "pubDate"

        val PUB_DATE_FORMAT = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)

        fun parse(parser: XmlPullParser) : Item? {
            var title: String = ""
            var link: String = ""
            var description = ""
            var pubDate: Date? = null

            parser.require(XmlPullParser.START_TAG, null, Item.TAG)

            while (true) {
                if (parser.next() == XmlPullParser.END_TAG && parser.name == Item.TAG) {
                    break
                }
                if (parser.eventType == XmlPullParser.START_TAG) {
                    val tagName = parser.name

                    when (tagName) {
                        PROP_TITLE -> title = readTextTag(parser, PROP_TITLE)
                        PROP_DESCRIPTION -> description = readTextTag(parser, PROP_DESCRIPTION)
                        PROP_LINK -> link = readTextTag(parser, PROP_LINK)
                        PROP_PUB_DATE -> {
                            val dateString = readTextTag(parser, PROP_PUB_DATE)

                            try {
                                pubDate = PUB_DATE_FORMAT.parse(dateString)
                            } catch (ex: Exception) {
                                Timber.e(ex, "Unable to parse date: $dateString")
                            }
                        }
                        else -> readAllOfTag(parser, tagName)
                    }
                }
            }

            parser.require(XmlPullParser.END_TAG, null, Item.TAG)

            return Item(title = title, description = description, link = link, pubDate = pubDate)
        }
    }
}


data class Channel(val title: String,
                   val description: String,
                   val items: List<Item>) {
    companion object {
        val TAG = "channel"

        val PROP_TITLE = "title"
        val PROP_DESCRIPTION = "description"

        fun parse(parser: XmlPullParser): Channel? {
            var title: String = ""
            var description: String = ""
            val items: MutableList<Item> = mutableListOf()

            parser.require(XmlPullParser.START_TAG, null, Channel.TAG)

            while (true) {
                if (parser.next() == XmlPullParser.END_TAG && parser.name == Channel.TAG) {
                    break
                }
                if (parser.eventType == XmlPullParser.START_TAG) {
                    val tagName = parser.name

                    when (tagName) {
                        PROP_TITLE -> title = readTextTag(parser, PROP_TITLE)
                        PROP_DESCRIPTION -> description = readTextTag(parser, PROP_DESCRIPTION)
                        Item.TAG -> {
                            val item = Item.parse(parser)
                            if (item != null) {
                                items.add(item)
                            }
                        }
                        else -> readAllOfTag(parser, tagName)
                    }
                }
            }

            parser.require(XmlPullParser.END_TAG, null, Channel.TAG)

            return Channel(title = title, description =  description, items = items)
        }
    }
}

data class Rss(val channel: Channel?) {
    companion object {
        val TAG = "rss"

        fun parse(parser: XmlPullParser) : Rss {
            parser.require(XmlPullParser.START_TAG, null, Rss.TAG)

            var channel: Channel? = null

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.eventType == XmlPullParser.START_TAG) {
                    var tagName = parser.name

                    if (tagName == Channel.TAG) {
                        channel = Channel.parse(parser)
                    } else {
                        readAllOfTag(parser, tagName)
                    }
                }
            }

            parser.require(XmlPullParser.END_TAG, null, Rss.TAG)

            return Rss(channel)
        }

        fun parseAsRoot(parser: XmlPullParser) : Rss {
            parser.nextTag()

            return parse(parser)
        }
    }
}

internal fun readTextTag(parser: XmlPullParser, tagName: String): String {
    parser.require(XmlPullParser.START_TAG, null, tagName)
    val title = readText(parser)
    parser.require(XmlPullParser.END_TAG, null, tagName)
    return title
}

internal fun readText(parser: XmlPullParser): String =
        if (parser.next() == XmlPullParser.TEXT) {
            val text = parser.text
            parser.nextTag()
            text
        } else {
            ""
        }

internal fun readAllOfTag(parser: XmlPullParser, tagName: String) {
    parser.require(XmlPullParser.START_TAG, null, tagName)

    while (true) {
        val next = parser.next()
        if (next == XmlPullParser.END_TAG || next == XmlPullParser.END_DOCUMENT) {
            break;
        }
        if (parser.eventType == XmlPullParser.START_TAG) {
            readAllOfTag(parser, parser.name)
        }
    }

    if (parser.eventType == XmlPullParser.END_TAG) {
        parser.require(XmlPullParser.END_TAG, null, tagName)
    }
}

