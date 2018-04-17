package com.github.sidky.bigpicture.di

import com.github.sidky.bigpicture.main.MainPresenter
import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext

val viewModule = applicationContext {
    context(ActivityModule.MAIN) {
        bean {
            MainPresenter(get())
        }
    }
}

object ActivityModule {
    val MAIN = "activity.main"
}