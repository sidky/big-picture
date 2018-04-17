package com.github.sidky.bigpicture.network

import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.sidky.bigpicture.common.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext

val networkModule = applicationContext {
    bean {
        HttpLoggingInterceptor()
    }

    bean {
        val builder = OkHttpClient.Builder()
                .addInterceptor(get<HttpLoggingInterceptor>())
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }
        builder.build()
    }

    bean("io") {
        Schedulers.io()
    }

    bean {
        NetworkHelper(get(), get("io"))
    }

    bean {
        FeedHelper(get(), get(), get(Constants.RSS_URL))
    }
}