package com.github.sidky.bigpicture.xml

import android.util.Xml
import org.koin.dsl.module.applicationContext
import org.xmlpull.v1.XmlPullParser

val xmlModule = applicationContext {
    bean {
        FeedParser()
    }
}