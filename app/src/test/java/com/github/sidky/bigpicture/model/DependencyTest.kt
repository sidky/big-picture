package com.github.sidky.bigpicture.model

import com.github.sidky.bigpicture.common.constantsModule
import com.github.sidky.bigpicture.di.viewModule
import com.github.sidky.bigpicture.network.networkModule
import com.github.sidky.bigpicture.xml.xmlModule
import org.junit.Test
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.KoinTest
import org.koin.test.dryRun

class DependencyTest : KoinTest {
    @Test fun dryRunTest() {
        startKoin(arrayListOf(constantsModule, networkModule, xmlModule, viewModule))

        dryRun()
    }
}