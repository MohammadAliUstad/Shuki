package com.yugentech.shuki.data.room

import android.app.Application

class ShukiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}