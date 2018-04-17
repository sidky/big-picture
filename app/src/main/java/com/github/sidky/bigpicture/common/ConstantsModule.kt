package com.github.sidky.bigpicture.common

import org.koin.dsl.module.applicationContext

val constantsModule = applicationContext {
    bean(Constants.RSS_URL) {
        "https://www.bostonglobe.com/rss/bigpicture"
    }
}

object Constants {
    val RSS_URL = "bigpicture.rss.url"
}