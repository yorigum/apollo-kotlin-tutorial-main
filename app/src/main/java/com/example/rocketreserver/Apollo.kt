package com.example.rocketreserver

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response




private class AuthorizationInterceptor(val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", User.getToken(context) ?: "")
            .build()

        return chain.proceed(request)
    }
}

private var instance: ApolloClient? = null

fun apolloClient(context: Context): ApolloClient {

    //reuse instance
    if (instance != null) {
        return instance!!
    }

    val chuckerInterceptor = ChuckerInterceptor.Builder(context)
        .collector(ChuckerCollector(context))
        .maxContentLength(250000L)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(false)
        .build()
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor(context))
        .addInterceptor(chuckerInterceptor)
        .build()

    instance = ApolloClient.Builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .okHttpClient(okHttpClient)
        .build()

    return instance!!
}