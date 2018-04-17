package com.github.sidky.bigpicture.main

import com.github.sidky.bigpicture.model.Item
import com.github.sidky.bigpicture.network.FeedHelper
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.koin.standalone.StandAloneContext

class MainPresenter(private val feedHelper: FeedHelper) {
    private val eventSubject: BehaviorSubject<ActivityScreen> = BehaviorSubject.create()
    private val compositeDisposable = CompositeDisposable()

    init {
        loadRss()
    }

    fun events(): Flowable<ActivityScreen> {
        TODO()
    }

    fun open(item: Item) {
    }

    private fun loadRss() {
        val disposable = feedHelper.loadRss().subscribe { rss ->
            eventSubject.onNext(ActivityScreen.RssScreen())
        }
        compositeDisposable.add(disposable)
    }
}

sealed class ActivityScreen {
    class RssScreen : ActivityScreen()
    class BigPictureScreen : ActivityScreen()
}