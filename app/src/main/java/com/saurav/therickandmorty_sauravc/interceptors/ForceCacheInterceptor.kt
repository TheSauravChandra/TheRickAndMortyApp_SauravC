package com.saurav.therickandmorty_sauravc.interceptors

import com.saurav.therickandmorty_sauravc.application.AppContext
import com.saurav.therickandmorty_sauravc.utils.Utils.Companion.checkInternet
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class ForceCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        if (!AppContext.getContext().checkInternet()) {
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }
        return chain.proceed(builder.build());
    }
}