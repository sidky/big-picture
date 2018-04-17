package com.github.sidky.bigpicture.data

import android.arch.persistence.room.Room
import org.koin.dsl.module.applicationContext

val dataModule = applicationContext {
    bean {
        Room.databaseBuilder(get(), BigPictureDb::class.java, "big-picture").build()
    }
}