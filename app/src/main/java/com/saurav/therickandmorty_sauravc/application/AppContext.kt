package com.saurav.therickandmorty_sauravc.application

import android.content.Context

object AppContext {
    private lateinit var appContext: Context

    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    fun getContext(): Context {
        if (!::appContext.isInitialized) {
            throw IllegalStateException("AppContext has not been initialized. Call initialize() first.")
        }
        return appContext
    }
}