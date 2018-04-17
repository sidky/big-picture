package com.github.sidky.bigpicture.network

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class NetworkHelper(private val client: OkHttpClient, private val scheduler: Scheduler) {
    fun load(url: String): Single<Response> {
        return Single.fromCallable {
            val request = Request.Builder().url(url).build()
            client.newCall(request).execute()
        }.subscribeOn(scheduler)
    }
}