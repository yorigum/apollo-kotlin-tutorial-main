package com.example.rocketreserver

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
    .build()